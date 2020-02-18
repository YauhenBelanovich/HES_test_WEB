package com.gmail.yauhen2012.service.impl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.gmail.yauhen2012.repository.ConnectionRepository;
import com.gmail.yauhen2012.repository.UserInformationRepository;
import com.gmail.yauhen2012.repository.UserRepository;
import com.gmail.yauhen2012.repository.impl.ConnectionRepositoryImpl;
import com.gmail.yauhen2012.repository.impl.UserInformationRepositoryImpl;
import com.gmail.yauhen2012.repository.impl.UserRepositoryImpl;
import com.gmail.yauhen2012.repository.model.User;
import com.gmail.yauhen2012.repository.model.UserInformation;
import com.gmail.yauhen2012.service.UserService;
import com.gmail.yauhen2012.service.model.AddUserDTO;
import com.gmail.yauhen2012.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static volatile UserService instance;
    private UserRepository userRepository;
    private UserInformationRepository userInformationRepository;
    private ConnectionRepository connectionRepository;

    private UserServiceImpl(
            ConnectionRepository connectionRepository,
            UserRepository userRepository,
            UserInformationRepository userInformationRepository) {
        this.connectionRepository = connectionRepository;
        this.userRepository = userRepository;
        this.userInformationRepository = userInformationRepository;
    }

    public static UserService getInstance() {
        UserService localInstance = instance;
        if (localInstance == null) {
            synchronized (UserService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UserServiceImpl(
                            ConnectionRepositoryImpl.getInstance(),
                            UserRepositoryImpl.getInstance(),
                            UserInformationRepositoryImpl.getInstance());
                }
            }
        }
        return localInstance;
    }

    @Override
    public void add(AddUserDTO addUserDTO) {

        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User toDatabaseUser = convertUserDTOToDatabaseUser(addUserDTO);
                User addedUser = userRepository.add(connection, toDatabaseUser);
                int databaseUserId = addedUser.getId();
                toDatabaseUser.getUserInformation().setUserId(databaseUserId);
                userInformationRepository.add(connection, toDatabaseUser.getUserInformation());
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<UserDTO> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<User> userList = userRepository.findAll(connection);
                List<UserDTO> userDTOList = convertDatabaseUserToDTO(userList);
                connection.commit();
                return userDTOList;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public int deleteUserById(Integer id) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                userInformationRepository.delete(connection, id);
                int affectedRows = userRepository.delete(connection, id);
                connection.commit();
                return affectedRows;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;

    }

    @Override
    public int setNewUserFirstName(String newFirstName, Integer id) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int affectedRows = userInformationRepository.updateUserFirstName(connection, newFirstName, id);
                connection.commit();
                return affectedRows;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;

    }

    @Override
    public int setNewUserLastName(String newLastName, Integer id) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int affectedRows = userInformationRepository.updateUserLastName(connection, newLastName, id);
                connection.commit();
                return affectedRows;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;

    }

    @Override
    public int changeUserStatus(Boolean newStatus, Integer id) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                int affectedRows = userRepository.updateUserStatus(connection, newStatus, id);
                connection.commit();
                return affectedRows;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;

    }

    @Override
    public boolean isValidUser(String userName, String password) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.findUserByUsername(connection, userName);
                connection.commit();
                if (user!=null && user.getActive()) {
                    return password.equals(user.getPassword());
                } else return false;

            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.findUserByUsername(connection, username);
                connection.commit();
                if (user != null) {
                    return convertObjectToDTO(user);
                }
                return null;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public UserDTO findUserById(Integer id) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = userRepository.findUserById(connection, id);
                connection.commit();
                if (user != null) {
                    return convertObjectToDTO(user);
                }
                return null;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private User convertUserDTOToDatabaseUser(AddUserDTO addUserDTO) {
        User toDatabaseUser = new User();
        toDatabaseUser.setUserName(addUserDTO.getUserName());
        toDatabaseUser.setPassword(addUserDTO.getPassword());
        toDatabaseUser.setActive(addUserDTO.getActive());
        toDatabaseUser.setRole(addUserDTO.getRole());

        UserInformation userInformation = new UserInformation();
        userInformation.setFirstName(addUserDTO.getFirstName());
        userInformation.setLastName(addUserDTO.getLastName());

        toDatabaseUser.setUserInformation(userInformation);

        return toDatabaseUser;
    }

    private List<UserDTO> convertDatabaseUserToDTO(List<User> userList) {
        return userList.stream()
                .map(this::convertObjectToDTO)
                .collect(Collectors.toList());
    }

    private UserDTO convertObjectToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setPassword(user.getPassword());
        userDTO.setActive(user.getActive());
        userDTO.setCreatedBy(user.getCreatedBy());
        userDTO.setRole(user.getRole());
        if (user.getUserInformation() != null) {
            userDTO.setFirstName(user.getUserInformation().getFirstName());
            userDTO.setLastName(user.getUserInformation().getLastName());
        }
        return userDTO;
    }
}

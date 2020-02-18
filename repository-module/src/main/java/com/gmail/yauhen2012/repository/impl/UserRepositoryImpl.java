package com.gmail.yauhen2012.repository.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.gmail.yauhen2012.repository.UserRepository;
import com.gmail.yauhen2012.repository.model.User;
import com.gmail.yauhen2012.repository.model.UserInformation;

public class UserRepositoryImpl extends GeneralRepositoryImpl<User> implements UserRepository {

    private static volatile UserRepository instance;

    private UserRepositoryImpl() {
    }

    public static UserRepository getInstance() {
        UserRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (UserRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UserRepositoryImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public int delete(Connection connection, Serializable id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM user WHERE id=?"
                )
        ) {
            statement.setInt(1, (Integer) id);
            return statement.executeUpdate();
        }
    }

    @Override
    public void updateUserStatus(Connection connection, Boolean newStatus, Integer id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE user SET is_active=? WHERE id=?"
                )
        ) {
            statement.setBoolean(1, newStatus);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

    @Override
    public List<User> findAll(Connection connection) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT u.id, username, password, is_active, role, createdBy, ui.first_name, ui.last_name FROM " +
                                "user u LEFT OUTER JOIN user_information ui ON u.id = ui.user_id;"
                )
        ) {
            List<User> userList = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    User user = getUser(rs);
                    userList.add(user);
                }
                return userList;
            }
        }
    }

    @Override
    public User add(Connection connection, User user) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO user (username, password, is_active, role) VALUES (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getPassword());
            statement.setBoolean(3, user.getActive());
            statement.setString(4, user.getRole());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
            return user;
        }
    }

    @Override
    public User findUserByUsername(Connection connection, String username) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT u.id, username, password, is_active, role, createdBy, ui.first_name, ui.last_name FROM user u " +
                                " LEFT OUTER JOIN user_information ui ON u.id = ui.user_id where u.username=?;"
                )
        ) {
            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return getUser(rs);
                }
                return null;
            }
        }
    }

    @Override
    public User findUserById(Connection connection, Integer id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT u.id, username, password, is_active, role, createdBy, ui.first_name, ui.last_name FROM user u " +
                                " LEFT OUTER JOIN user_information ui ON u.id = ui.user_id where u.id=?;"
                )
        ) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return getUser(rs);
                }
                return null;
            }
        }
    }

    private User getUser(ResultSet rs) throws SQLException {
        User user = new User();
        int id = rs.getInt("id");
        user.setId(id);

        String username = rs.getString("username");
        user.setUserName(username);

        String password = rs.getString("password");
        user.setPassword(password);

        Boolean isActive = rs.getBoolean("is_active");
        user.setActive(isActive);

        String createdBy = rs.getString("createdBy");
        user.setCreatedBy(createdBy);

        String role = rs.getString("role");
        user.setRole(role);

        UserInformation userInformation = new UserInformation();
        userInformation.setFirstName(rs.getString("first_name"));
        userInformation.setLastName(rs.getString("last_name"));
        user.setUserInformation(userInformation);

        return user;
    }

}

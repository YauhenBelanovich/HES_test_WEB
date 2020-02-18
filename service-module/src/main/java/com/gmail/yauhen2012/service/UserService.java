package com.gmail.yauhen2012.service;

import java.util.List;

import com.gmail.yauhen2012.service.model.AddUserDTO;
import com.gmail.yauhen2012.service.model.UserDTO;

public interface UserService {

    void add(AddUserDTO addUserDTO);

    List<UserDTO> findAll();

    int deleteUserById(Integer id);

    int setNewUserFirstName(String newFirstName, Integer id);

    int setNewUserLastName(String newLastName, Integer id);

    int changeUserStatus(Boolean newStatus, Integer id);

    boolean isValidUser(String username, String password);

    UserDTO findUserByUsername(String username);

    UserDTO findUserById(Integer id);

}

package com.gmail.yauhen2012.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.yauhen2012.repository.model.User;

public interface UserRepository extends GeneralRepository<User> {

    List<User> findAll(Connection connection) throws SQLException;

    int delete(Connection connection, Serializable id) throws SQLException;

    int updateUserStatus(Connection connection, Boolean newStatus, Integer id) throws SQLException;

    User findUserByUsername(Connection connection, String username) throws SQLException;

    User findUserById(Connection connection, Integer id) throws SQLException;

}

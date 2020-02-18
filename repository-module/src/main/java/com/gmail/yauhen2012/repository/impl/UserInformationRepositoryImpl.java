package com.gmail.yauhen2012.repository.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gmail.yauhen2012.repository.UserInformationRepository;
import com.gmail.yauhen2012.repository.model.UserInformation;

public class UserInformationRepositoryImpl extends GeneralRepositoryImpl<UserInformation> implements UserInformationRepository {

    private static volatile UserInformationRepository instance;

    private UserInformationRepositoryImpl() {
    }

    public static UserInformationRepository getInstance() {
        UserInformationRepository localInstance = instance;
        if (localInstance == null) {
            synchronized (UserInformationRepository.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new UserInformationRepositoryImpl();
                }
            }
        }
        return localInstance;
    }

    @Override
    public int delete(Connection connection, Serializable id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM user_information WHERE user_id=?"
                )
        ) {
            statement.setInt(1, (Integer) id);
            return statement.executeUpdate();
        }
    }

    @Override
    public void updateUserFirstName(Connection connection, String newUserFirstName, Integer id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE user_information SET first_name=? WHERE user_id=?"
                )
        ) {
            statement.setString(1, newUserFirstName);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

    @Override
    public void updateUserLastName(Connection connection, String newUserLastName, Integer id) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE user_information SET last_name=? WHERE user_id=?"
                )
        ) {
            statement.setString(1, newUserLastName);
            statement.setInt(2, id);
            statement.executeUpdate();
        }
    }

    @Override
    public UserInformation add(Connection connection, UserInformation userInformation) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO user_information(user_id, first_name, last_name) VALUES (?,?,?)"
                )
        ) {
            statement.setInt(1, userInformation.getUserId());
            statement.setString(2, userInformation.getFirstName());
            statement.setString(3, userInformation.getLastName());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user information failed, no rows affected.");
            }
            return userInformation;
        }
    }

}

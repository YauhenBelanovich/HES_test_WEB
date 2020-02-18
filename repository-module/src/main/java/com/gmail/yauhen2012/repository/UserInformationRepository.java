package com.gmail.yauhen2012.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import com.gmail.yauhen2012.repository.model.UserInformation;

public interface UserInformationRepository extends GeneralRepository<UserInformation> {

    int delete(Connection connection, Serializable id) throws SQLException;

    int updateUserFirstName(Connection connection, String newUserFirstName, Integer id) throws SQLException;

    int updateUserLastName(Connection connection, String newUserLastName, Integer id) throws SQLException;

}

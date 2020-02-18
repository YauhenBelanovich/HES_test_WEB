package com.gmail.yauhen2012.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gmail.yauhen2012.repository.model.Role;

public interface RoleRepository extends GeneralRepository<Role> {

    List<Role> findAll(Connection connection) throws SQLException;
}

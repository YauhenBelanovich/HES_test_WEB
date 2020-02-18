package com.gmail.yauhen2012.controller.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.gmail.yauhen2012.service.RoleService;
import com.gmail.yauhen2012.service.TableService;
import com.gmail.yauhen2012.service.UserService;
import com.gmail.yauhen2012.service.impl.RoleServiceImpl;
import com.gmail.yauhen2012.service.impl.TableServiceImpl;
import com.gmail.yauhen2012.service.impl.UserServiceImpl;
import com.gmail.yauhen2012.service.model.AddUserDTO;

public class InitListener implements ServletContextListener {

    private TableService tableService = TableServiceImpl.getInstance();
    private RoleService roleService = RoleServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}

    public void contextInitialized(ServletContextEvent sce) {

        tableService.deleteAllTables();
        tableService.createAllTables();
        roleService.createRoles();

        AddUserDTO adminUser = new AddUserDTO();
        adminUser.setUserName("Yauhen");
        adminUser.setPassword("test1");
        adminUser.setActive(true);
        adminUser.setRole("ADMIN");
        adminUser.setFirstName("Yauheni");
        adminUser.setLastName("Belanovich");
        userService.add(adminUser);
    }

}

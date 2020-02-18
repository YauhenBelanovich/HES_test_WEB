package com.gmail.yauhen2012.controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.yauhen2012.service.UserService;
import com.gmail.yauhen2012.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatusServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        String status = req.getParameter("active");

        try {
            int userId = Integer.parseInt(id);
            if (Boolean.parseBoolean(status)) {
                userService.changeUserStatus(false, userId);
            } else {
                userService.changeUserStatus(true, userId);
            }
            String path = req.getContextPath() + "/user?id=" + id;
            resp.sendRedirect(path);
        } catch (NumberFormatException e) {
            logger.error("Incorrect format", e);
        }
    }

}

package com.gmail.yauhen2012.controller;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.yauhen2012.controller.validator.Validation;
import com.gmail.yauhen2012.controller.validator.impl.ValidationImpl;
import com.gmail.yauhen2012.service.UserService;
import com.gmail.yauhen2012.service.impl.UserServiceImpl;
import com.gmail.yauhen2012.service.model.AddUserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gmail.yauhen2012.controller.constant.PageConstant.PAGES_LOCATION;

public class UserRegistrationServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();
    private Validation validation = ValidationImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(PAGES_LOCATION + "/new.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("username");
        String password = req.getParameter("password");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String role = req.getParameter("role");
        String isActive = req.getParameter("isActive");

        if (isValidName(name) && isValidPassword(password) && isValidRole(role)
                && isValidStatus(isActive) && isValidFirstLastName(firstName)
                && isValidFirstLastName(lastName)) {

            try {
                AddUserDTO addUserDTO = new AddUserDTO();
                addUserDTO.setUserName(name);
                addUserDTO.setPassword(password);
                addUserDTO.setFirstName(firstName);
                addUserDTO.setLastName(lastName);
                addUserDTO.setRole(role);
                boolean isActiveBoolean = Boolean.parseBoolean(isActive);
                addUserDTO.setActive(isActiveBoolean);

                userService.add(addUserDTO);
                String path = req.getContextPath() + "/users";
                resp.sendRedirect(path);
            } catch (NumberFormatException e) {
                logger.error("Incorrect format", e);
            }
        } else {
            ServletContext context = getServletContext();
            RequestDispatcher dispatcher = context.getRequestDispatcher(PAGES_LOCATION + "/new.jsp");
            dispatcher.forward(req, resp);
        }

    }

    private Boolean isValidPassword(String password) {
        return validation.passwordValidation(password) == null;
    }

    private Boolean isValidName(String userName) {
        return validation.nameValidation(userName) == null;
    }

    private Boolean isValidStatus(String isActive) {
        return validation.booleanValidation(isActive) == null;
    }

    private Boolean isValidRole(String role) {
        return validation.roleValidation(role) == null;
    }

    private Boolean isValidFirstLastName(String name) {
        return validation.firstLastNameValidation(name) == null;
    }

}


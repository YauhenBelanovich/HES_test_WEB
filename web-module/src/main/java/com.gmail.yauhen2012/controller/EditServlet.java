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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gmail.yauhen2012.controller.constant.PageConstant.PAGES_LOCATION;

public class EditServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();
    private Validation validation = ValidationImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher(PAGES_LOCATION + "/edit.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getParameter("id");
        String newFirstName = req.getParameter("newFirstName");
        String newLastName = req.getParameter("newLastName");

        try {
            int userId = Integer.parseInt(id);
            if (!newFirstName.isEmpty() && isValidName(newFirstName)) {
                userService.setNewUserFirstName(newFirstName, userId);
            } else {
                logger.error("New first Name s empty, or invalid");
            }
            if (!newLastName.isEmpty() && isValidName(newLastName)) {
                userService.setNewUserLastName(newLastName, userId);
            } else {
                logger.error("New last Name s empty, or invalid");
            }
            String path = req.getContextPath() + "/user?id=" + id;
            resp.sendRedirect(path);
        } catch (NumberFormatException e) {
            logger.error("Incorrect format", e);
        }
    }

    private Boolean isValidName(String name) {
        return validation.firstLastNameValidation(name) == null;
    }

}

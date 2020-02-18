package com.gmail.yauhen2012.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gmail.yauhen2012.service.UserService;
import com.gmail.yauhen2012.service.impl.UserServiceImpl;
import com.gmail.yauhen2012.service.model.UserDTO;

import static com.gmail.yauhen2012.controller.constant.PageConstant.PAGES_LOCATION;

public class ViewUserServlet extends HttpServlet {

    private UserService userService = UserServiceImpl.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String stringId = request.getParameter("id");
        Integer id = Integer.parseInt(stringId);
        UserDTO userById = userService.findUserById(id);
        request.setAttribute("userById", userById);

        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(PAGES_LOCATION + "/user.jsp");

        requestDispatcher.forward(request, response);
    }

}

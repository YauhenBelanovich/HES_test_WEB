package com.gmail.yauhen2012.controller.filter;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gmail.yauhen2012.service.UserService;
import com.gmail.yauhen2012.service.impl.UserServiceImpl;
import com.gmail.yauhen2012.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private UserService userService = UserServiceImpl.getInstance();

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        logger.info("LoginFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) req;
        String username = httpRequest.getParameter("username");
        String password = httpRequest.getParameter("password");
        if (username == null) {
            chain.doFilter(req, resp);
        } else {
            boolean isValid = userService.isValidUser(username, password);
            if (isValid) {
                UserDTO user = userService.findUserByUsername(username);
                HttpSession session = httpRequest.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userRole", user.getRole());
                ((HttpServletResponse) resp).sendRedirect(httpRequest.getContextPath() + "/users");
            } else {
                ((HttpServletResponse) resp).sendRedirect(httpRequest.getContextPath() + "/login?is_not_valid=true");
            }
        }
    }

}

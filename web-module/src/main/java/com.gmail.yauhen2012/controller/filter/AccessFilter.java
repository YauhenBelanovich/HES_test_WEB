package com.gmail.yauhen2012.controller.filter;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gmail.yauhen2012.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.gmail.yauhen2012.controller.constant.ServletConstant.ADMIN_ROLE;
import static com.gmail.yauhen2012.controller.constant.ServletConstant.USER_ROLE;

public class AccessFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        logger.info("AccessFilter");
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpSession session = httpRequest.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            String roleName = user.getRole();
            List<String> userAddresses = AccessFilter.getBannedAddressesForRole(roleName);
            if (userAddresses.stream().noneMatch(s -> httpRequest.getRequestURI().contains(s))) {
                chain.doFilter(req, resp);
            } else {
                session.removeAttribute("user");
                session.removeAttribute("userRole");
                ((HttpServletResponse) resp).sendRedirect(httpRequest.getContextPath() + "/login");
            }
        } else {
            ((HttpServletResponse) resp).sendRedirect(httpRequest.getContextPath() + "/login?is_not_valid=true");
        }

    }

    private static final Map<String, List<String>> roleMap = new HashMap<String, List<String>>() {{
        List<String> listAdminBannedPages = new ArrayList<>();
        listAdminBannedPages.add("/adminCanGoToAnyAddressButNotToThis");

        List<String> listUserBannedPages = new ArrayList<>();
        listUserBannedPages.add("/new");
        listUserBannedPages.add("/edit");

        put(ADMIN_ROLE, listAdminBannedPages);
        put(USER_ROLE, listUserBannedPages);
    }};

    private static List<String> getBannedAddressesForRole(String roleName) {
        return roleMap.get(roleName);
    }

}

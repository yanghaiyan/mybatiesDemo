package com.mybatis.demo.filter;

import com.mybatis.demo.constant.SessionConstant;
import com.mybatis.demo.entity.User;
import com.mybatis.demo.utils.AppContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "appContextFilter", urlPatterns = {"/*"})
public class AppContextFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        AppContext.setContextPath(request.getContextPath());

        AppContext appContext = AppContext.getContext();

        HttpSession session = request.getSession();


        String userName = (String) session.getAttribute(SessionConstant.USER_SESSION);
        appContext.addObject(SessionConstant.APP_CONTEXT_USER, userName);

        appContext.addObject(SessionConstant.APP_CONTEXT_SESSION, session);

        try {
            filterChain.doFilter(request, response);
        } finally {
            appContext.clear();
        }
    }
}

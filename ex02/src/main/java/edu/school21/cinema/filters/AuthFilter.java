package edu.school21.cinema.filters;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/profile", "/signIn", "/signUp"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse resp = (HttpServletResponse) servletResponse;
        final String requestURI = req.getRequestURI();

        final HttpSession session = req.getSession();

        if(!StringUtils.isEmpty(requestURI) &&
                ((requestURI.contains("signIn") || requestURI.contains("signUp"))
                        && session.getAttribute("user") != null)){
            resp.sendRedirect("profile");
            return;
        }
        if (session.getAttribute("user") == null && (requestURI.contains("profile") || requestURI.contains("upload"))){
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

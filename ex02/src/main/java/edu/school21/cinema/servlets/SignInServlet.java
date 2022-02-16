package edu.school21.cinema.servlets;

import edu.school21.cinema.services.UserHandler;
import org.springframework.context.ApplicationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {

    private static final String SIGN_IN_URL = "/WEB-INF/html/signIn.html";

    public static final Logger LOGGER = LoggerFactory.getLogger(SignUpServlet.class);

    private ApplicationContext springContext;


    @Override
    public void init(ServletConfig config) {
        springContext = (ApplicationContext) config.getServletContext().getAttribute("springContext");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(SIGN_IN_URL).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserHandler userHandler = springContext.getBean("userHandler", UserHandler.class);
        final String EMAIL = req.getParameter("email");

        if (userHandler.read(EMAIL, req.getParameter("password"))){
            HttpSession session = req.getSession();
            try {
                userHandler.setAuth(EMAIL, req.getRemoteAddr());
            } catch (SQLException e) {
                LOGGER.error("SQL error: " + e.getSQLState());
            }
            session.setAttribute("user", userHandler.get(EMAIL));
            session.setAttribute("authArr", userHandler.getAuth(EMAIL));
            resp.sendRedirect("profile");
        }
        else doGet(req, resp);
    }
}
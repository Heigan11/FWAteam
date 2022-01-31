package edu.school21.cinema.servlets;

import edu.school21.cinema.models.User;
import edu.school21.cinema.repositories.UserDAO;
import edu.school21.cinema.services.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    private final String SIGNUP_HTML = "/WEB-INF/html/signUp.html";
    private ApplicationContext springContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        springContext = (ApplicationContext) config.getServletContext().getAttribute("springContext");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(SIGNUP_HTML).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserHandler userHandler = springContext.getBean("userHandler", UserHandler.class);

        try {
            if (userHandler.create(req.getParameter("name"), req.getParameter("surname"), req.getParameter("phone"),
                    req.getParameter("email"), req.getParameter("password")))
                req.getRequestDispatcher("/WEB-INF/html/registered.html").forward(req, resp);
            else
                req.getRequestDispatcher(SIGNUP_HTML).forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() { }
}
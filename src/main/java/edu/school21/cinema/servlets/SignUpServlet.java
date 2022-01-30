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

//    @Autowired
//    private PasswordEncoder passwordEncoder;

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

//        UserDAO userDAO = springContext.getBean("userDAO", UserDAO.class);
        UserHandler userHandler = springContext.getBean("userHandler", UserHandler.class);

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            if (userHandler.create(name, surname, phone, email, password))
                req.getRequestDispatcher("/WEB-INF/html/registered.html").forward(req, resp);
            else
                req.getRequestDispatcher(SIGNUP_HTML).forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        String password = passwordEncoder.encode(req.getParameter("password"));

//        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(surname) &&
//                !StringUtils.isEmpty(phone) && !StringUtils.isEmpty(email) && !StringUtils.isEmpty(password)) {
////            req.getRequestDispatcher("/WEB-INF/html/registered.html").forward(req, resp);
//            if (!userDAO.findByEmail(email).isPresent()) {
//                try {
//                    userDAO.saveUser(new User(name, surname, phone, email, password));
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                req.getRequestDispatcher(userDAO.showTable().toString()).forward(req, resp);
//                return;
//            }
//        }
//        req.getRequestDispatcher(SIGNUP_HTML).forward(req, resp);
    }

    @Override
    public void destroy() { }
}
package edu.school21.cinema.servlets;

import edu.school21.cinema.models.User;
import edu.school21.cinema.services.UserHandler;
import org.apache.tika.Tika;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@WebServlet("/setAvatar/*")
public class avatarServlet extends HttpServlet {

    private ApplicationContext springContext;

    @Override
    public void init(ServletConfig config) {
        springContext = (ApplicationContext) config.getServletContext().getAttribute("springContext");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String imagePath = session.getAttribute("storagePath") +
                req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1);

        UserHandler userHandler = springContext.getBean("userHandler", UserHandler.class);
        User user = ((Optional<User>) session.getAttribute("user")).get();

        userHandler.setAvatar(user.getEmail(), imagePath);

        session.removeAttribute("image");
        session.removeAttribute("mimeType");
        resp.reset();
        resp.sendRedirect("/profile");
    }
}
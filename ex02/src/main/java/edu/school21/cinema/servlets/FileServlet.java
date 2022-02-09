package edu.school21.cinema.servlets;

import edu.school21.cinema.models.User;
import edu.school21.cinema.services.UserHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/upload")
@MultipartConfig()
public class FileServlet extends HttpServlet {

    private ApplicationContext springContext;

    @Override
    public void init(ServletConfig config) {
        springContext = (ApplicationContext) config.getServletContext().getAttribute("springContext");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        User user = ((Optional<User>) session.getAttribute("user")).get();

        String storage_path = springContext.getBean("getStoragePath", String.class);
        storage_path = storage_path + user.getEmail() + "/";
        File f = new File(storage_path);
        if (!f.exists())
            if (!f.mkdir())
                return;

        System.out.println(storage_path);
        for (Part part : req.getParts()) {
            try {
                part.write((storage_path + part.getSubmittedFileName()));
            }
            catch (Exception ignored) { }
        }
        resp.sendRedirect("/profile");
    }
}

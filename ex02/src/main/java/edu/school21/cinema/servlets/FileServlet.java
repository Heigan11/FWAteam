package edu.school21.cinema.servlets;

import edu.school21.cinema.models.User;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/images")
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 100)
public class FileServlet extends HttpServlet {

    private ApplicationContext springContext;

    @Override
    public void init(ServletConfig config) {
        springContext = (ApplicationContext) config.getServletContext().getAttribute("springContext");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        User user = ((Optional<User>) session.getAttribute("user")).get();

        String storage_path = springContext.getBean("getStoragePath", String.class);
        storage_path = storage_path + user.getEmail() + "/";


        for (Part part : req.getParts()) {
            try {
                if (part.getContentType().equals("image/png") ||
                        part.getContentType().equals("image/jpeg") ||
                        part.getContentType().equals("image/webp"))
                part.write((storage_path + part.getSubmittedFileName()));
            }
            catch (Exception ignored) { }
        }
        resp.sendRedirect("/profile");
    }
}

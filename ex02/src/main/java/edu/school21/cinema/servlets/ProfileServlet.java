package edu.school21.cinema.servlets;

import edu.school21.cinema.models.User;
import edu.school21.cinema.services.UserHandler;
import org.apache.tika.Tika;
import org.springframework.context.ApplicationContext;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private ApplicationContext springContext;

    @Override
    public void init(ServletConfig config) {
        springContext = (ApplicationContext) config.getServletContext().getAttribute("springContext");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserHandler userHandler = springContext.getBean("userHandler", UserHandler.class);
        HttpSession session = req.getSession();
        session.setAttribute("user", userHandler.get(((Optional<User>) session.getAttribute("user")).get().getEmail()));
        User user = ((Optional<User>) session.getAttribute("user")).get();

        session.setAttribute("authUser", user);

        String storage_path = springContext.getBean("getStoragePath", String.class);
        String defaultImg = storage_path + "no-img.jpg";
        storage_path = storage_path + user.getEmail() + "/";
        File f = new File(storage_path);
        if (!f.exists())
            if (!f.mkdir())
                req.getRequestDispatcher("/WEB-INF/html/signIn.html").forward(req, resp);

        session.setAttribute("storagePath", storage_path);

        Tika tika = new Tika();
        File img;
        if (!user.getAvatar().isEmpty()) {
            img = new File(user.getAvatar());
            if (!tika.detect(img).split("/")[0].equals("image"))
                img = new File(defaultImg);
        }
        else {
            img = new File(defaultImg);
        }

        String mimeType = tika.detect(img);
        byte[] fileContent = FileUtils.readFileToByteArray(img);
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        req.setAttribute("image", encodedString);
        req.setAttribute("mimeType", mimeType);


        ArrayList<String> uplodedFiles = new ArrayList<>();
        if (Objects.requireNonNull(f.listFiles()).length != 0) {
            for (File file : f.listFiles()) {
                mimeType = tika.detect(file);
                uplodedFiles.add(file.getName() + " " + file.length() + " " + mimeType);
            }
            session.setAttribute("uplodedFiles", uplodedFiles);
        }
        req.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(req, resp);
    }
}
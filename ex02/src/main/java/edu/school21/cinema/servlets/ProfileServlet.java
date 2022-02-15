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

    private static final String PROFILE_URL = "/WEB-INF/jsp/profile.jsp";

    private File setImg(User user, String defaultImg) throws IOException {
        Tika tika = new Tika();
        File img;
        if (!user.getAvatar().isEmpty()) {
            img = new File(user.getAvatar());
            if (!tika.detect(img).equals("image/png") &&
                    !tika.detect(img).equals("image/jpeg") &&
                    !tika.detect(img).equals("image/webp"))
                img = new File(defaultImg);
        } else
            img = new File(defaultImg);
        return img;
    }

    private void setMimeType(File img, HttpSession session) throws IOException {
        Tika tika = new Tika();
        String mimeType = tika.detect(img);
        session.setAttribute("mimeType", mimeType);
    }

    private void setEncodedString(File img, HttpSession session) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(img);
        String encodedString = Base64.getEncoder().encodeToString(fileContent);
        session.setAttribute("image", encodedString);
    }

    private void setUploadedFiles(File f, HttpSession session) throws IOException {
        Tika tika = new Tika();
        String mimeType;
        ArrayList<String> uplodedFiles = new ArrayList<>();
        if (Objects.requireNonNull(f.listFiles()).length != 0) {
            for (File file : Objects.requireNonNull(f.listFiles())) {
                mimeType = tika.detect(file);
                uplodedFiles.add(file.getName() + " " + file.length() + " " + mimeType);
            }
            session.setAttribute("uploadedFiles", uplodedFiles);
        }
    }

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
//                req.getRequestDispatcher("/WEB-INF/html/signIn.html").forward(req, resp);
                resp.sendRedirect("/signIn");

        session.setAttribute("storagePath", storage_path);

        File img = setImg(user, defaultImg);

        setMimeType(img, session);
        setEncodedString(img, session);
        setUploadedFiles(f, session);

        req.getRequestDispatcher(PROFILE_URL).forward(req, resp);
    }
}

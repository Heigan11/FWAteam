package edu.school21.cinema.servlets;

import org.apache.tika.Tika;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;

@WebServlet("/FWA/images/*")
public class PictureServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String imagePath = session.getAttribute("storagePath") +
                req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1);

        Tika tika = new Tika();
        File file = new File(imagePath);

        String mimeType = tika.detect(file);

        resp.reset();
        resp.setContentType(mimeType);
        resp.setHeader("Content-Length", String.valueOf(file.length()));

        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
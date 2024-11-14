package ru.kpfu.itis.server;

import ru.kpfu.itis.dto.UserDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;


@WebServlet(name = "userServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {

    private static final Logger LOG  = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Handling GET request for users");
        req.setAttribute("users", List.of(new UserDto("Ivan", 100, "Ivan228")));
        req.getRequestDispatcher("users.ftl").forward(req, resp);
    }
}

package ru.kpfu.itis.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.logging.Logger;


//если делать авторизацию то делать ее через куки лучше



@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private static final Logger LOG  = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Handling GET request for login");
        resp.sendRedirect("index.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Handling POST request for login");

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if ("login".equalsIgnoreCase(login) && "password".equals(password)) {
            LOG.info("Authentication successful for user: {}" + login);
            //session
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("user", login);
            httpSession.setMaxInactiveInterval(60*60);


            //cookie
            Cookie cookie = new Cookie("user", login);
            cookie.setMaxAge(24*60*60);
            resp.addCookie(cookie); //для того чтоб браузер узнал, что кука добавилась
            resp.sendRedirect("main.jsp");
        } else {
            LOG.info("Authentication failed for user: {}" + login);
            resp.sendRedirect("/login");
        }
    }
}

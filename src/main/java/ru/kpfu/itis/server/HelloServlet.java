package ru.kpfu.itis.server;

import ru.kpfu.itis.http.HttpClientImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("")
public class HelloServlet extends HttpServlet {

    private static final Logger LOG  = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Handling GET request");

        HttpClientImpl client = new HttpClientImpl();
        String url = "https://jsonplaceholder.typicode.com/posts?";

        Map<String, String> params = new HashMap<>();
        params.put("userId", "2");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        String response = client.get(url, headers, params);

        resp.setContentType("application/json");
        resp.getWriter().write(response);

        LOG.info("GET request handled successfully");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Handling POST request");

        HttpClientImpl client = new HttpClientImpl();
        String url = "https://jsonplaceholder.typicode.com/posts";

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer 58762cdab4e248c10d165f6bbe89d18a444dff00267b6cfcec49acf9dceb94b7");

        Map<String, String> data = new HashMap<>();
        data.put("userId", "78");
        data.put("id", "78");
        data.put("title", "title");
        data.put("body", "body");

        String response = client.post(url, headers, data);

        resp.setContentType("application/json");
        resp.getWriter().write(response);

        LOG.info("POST request handled successfully");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Handling PUT request");

        HttpClientImpl client = new HttpClientImpl();
        String url = "https://jsonplaceholder.typicode.com/posts/1";

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer 58762cdab4e248c10d165f6bbe89d18a444dff00267b6cfcec49acf9dceb94b7");

        Map<String, String> data = new HashMap<>();
        data.put("title", "new title");

        String response = client.put(url, headers, data);

        resp.setContentType("application/json");
        resp.getWriter().write(response);

        LOG.info("PUT request handled successfully");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Handling DELETE request");

        HttpClientImpl client = new HttpClientImpl();
        String url = "https://jsonplaceholder.typicode.com/posts/1";

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Map<String, String> data = new HashMap<>();

        System.out.println(client.delete(url, headers, data));

        LOG.info("DELETE request handled successfully");
    }
}

package ru.kpfu.itis.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@WebFilter(urlPatterns = "/*")
public class LoggingFilter implements Filter {

    private ServletContext servletContext;
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.servletContext = filterConfig.getServletContext();
        this.servletContext.log("LoggingFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String uri = httpServletRequest.getRequestURI();
        Map<String, String[]> params = request.getParameterMap();

        if (params != null) {
            String paramStr = params.entrySet().stream().map(
                            e -> e.getKey() + "=" + Arrays.toString(e.getValue()))
                    .collect(Collectors.joining(", ", "{", "}"));
            this.servletContext.log(request.getRemoteAddr() + " : request params: " + paramStr);
        }

        chain.doFilter(request, response);
    }
}
package com.smilehappiness.framework.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 跨域请求设置
 * </p>
 *
 * @author smilehappiness
 * @since 2021-10-02
 */
@Component
@WebFilter(filterName = "corsFilter", urlPatterns = "/*")
public class CorsFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        String origin = request.getHeader("Origin");
        if (StringUtils.isEmpty(origin)) {
            origin = request.getHeader("Referer");
        }

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        String allowHeaders = "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With, Authorization";
        response.setHeader("Access-Control-Allow-Headers", allowHeaders);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Content-type", "application/json");
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}

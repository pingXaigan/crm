package com.aigan.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author aigan
 * @date 2021/10/25 23:48
 */
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("过滤器开始工作");

        servletRequest.setCharacterEncoding("utf-8");

        servletResponse.setContentType("text/html;charset=utf-8");

        // 将请求放行

        filterChain.doFilter(servletRequest,servletResponse);
    }
}

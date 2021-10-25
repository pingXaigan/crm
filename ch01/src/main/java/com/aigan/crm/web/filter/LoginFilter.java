package com.aigan.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author aigan
 * @date 2021/10/26 0:35
 */
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("验证有没有登陆过（有没有session）");

    }
}

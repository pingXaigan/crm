package com.aigan.crm.web.filter;

import com.aigan.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author aigan
 * @date 2021/10/26 0:35
 */
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("验证有没有登陆过（有没有session）");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getServletPath();
        System.out.println(path);
        if("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)) {
            // 不应该拦截的资源，放行
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            // 其他资源
            HttpSession session =  request.getSession();
            User user = (User) session.getAttribute("user");

            if(user != null) {
                // user不为空，说明登录过
                filterChain.doFilter(servletRequest, servletResponse);
            }else {
                // 重定向到登录页
            /*
                ***重定向一律使用绝对路径

                为什么使用重定向，使用转发不行吗？
                    转发之后，路径会停留在老路径上，而不是跳转之后的最新资源的路径
             */
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }


    }
}

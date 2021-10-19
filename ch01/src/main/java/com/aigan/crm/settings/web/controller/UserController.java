package com.aigan.crm.settings.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author aigan
 * @date 2021/10/19 21:34
 */
public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到用户控制器");
        String path = request.getServletPath();
        if("/settings/user/xx.do".equals(path)){

        }else if("/settings/user/xx.do".equals(path)){

        }
    }
}

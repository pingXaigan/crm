package com.aigan.crm.workbench.web.controller;

import com.aigan.crm.settings.domain.User;
import com.aigan.crm.settings.service.UserService;
import com.aigan.crm.settings.service.impl.UserServiceImpl;
import com.aigan.crm.utils.PrintJson;
import com.aigan.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * @author aigan
 * @date 2021/10/19 21:34
 */
public class ActivityController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到市场活动控制器");
        String path = request.getServletPath();
        if("/workbench/activity/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if("/workbench/activity/xxx.do".equals(path)){

        }
    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = us.getUserList();

        PrintJson.printJsonObj(response,userList);
    }

}

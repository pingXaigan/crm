package com.aigan.crm.settings.web.controller;

import com.aigan.crm.settings.domain.User;
import com.aigan.crm.settings.service.UserService;
import com.aigan.crm.settings.service.impl.UserServiceImpl;
import com.aigan.crm.utils.MD5Util;
import com.aigan.crm.utils.PrintJson;
import com.aigan.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;


/**
 * @author aigan
 * @date 2021/10/19 21:34
 */
public class UserController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到用户控制器");
        String path = request.getServletPath();
        if("/settings/user/login.do".equals(path)){
            login(request,response);
        }else if("/settings/user/xx.do".equals(path)){

        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到验证登录操作");

        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        // 将密码的明文形式转换为MD5密文形式
        loginPwd = MD5Util.getMD5(loginPwd);
        // 接收浏览器端的IP地址
        String ip = request.getRemoteAddr();
        System.out.println("llq ip ----> " + ip);

        // 业务层开发，统一使用代理类形态的接口对象
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        try {
            User user = us.login(loginAct,loginPwd,ip);
            request.getSession().setAttribute("user",user);
            // 程序执行到此处，说明业务层没有为controller抛出任何异常
            /*
                {"success",true} 表示登录成功
                PrintJson 为自制util，目的是将对象转换成json串
             */
            PrintJson.printJsonFlag(response,true);
        } catch (Exception e) {
            e.printStackTrace();
            /*
            一旦程序执行了catch块的信息，说明登录失败
                {"success" : false ,"msg" : ?}
             */

            String msg = e.getMessage();

            /*
                现在需要为ajax请求提供更多的信息，失败的原因

                可以有两种手段来处理
                    1·将多项信息打包成map，将map解析成json串
                    2·创建一个VO
                        private boolean success;
                        private String msg;

                    如果对于展现的信息将来还会大量的使用，我们创建一个VO类，使用方便
                    如果只有在这个需求中能使用，使用map就可以了
             */

            Map<String,Object> map = new HashMap<String,Object>();
            map.put("success",false);
            map.put("msg",msg);
            PrintJson.printJsonObj(response,map);
        }
    }


}

package com.aigan.crm.workbench.web.controller;

import com.aigan.crm.settings.domain.User;
import com.aigan.crm.settings.service.UserService;
import com.aigan.crm.settings.service.impl.UserServiceImpl;
import com.aigan.crm.utils.DateTimeUtil;
import com.aigan.crm.utils.PrintJson;
import com.aigan.crm.utils.ServiceFactory;
import com.aigan.crm.utils.UUIDUtil;
import com.aigan.crm.vo.PaginationVO;
import com.aigan.crm.workbench.domain.Activity;
import com.aigan.crm.workbench.service.ActivityService;
import com.aigan.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        }else if("/workbench/activity/save.do".equals(path)){
            save(request,response);
        }else if("/workbench/activity/pageList.do".equals(path)){
            pageList(request,response);
        }
    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到查询市场活动信息列表的操作");

        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        //String pageNoStr = request.getParameter("pageNo");
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        //String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));

        // 计算略过的记录条数
        int skipCount = (pageNo - 1) * pageSize;

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        PaginationVO<Activity> vo = as.pageList(map);

        PrintJson.printJsonObj(response,vo);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行市场活动添加操作");

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        // 创建时间 当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        // 创建人 当前登录用户
        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        // 属性太多，创建一个对象用来存放这些属性
        Activity a = new Activity();
        a.setId(id);
        a.setOwner(owner);
        a.setName(name);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setCost(cost);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        boolean flag = as.save(a);
        PrintJson.printJsonFlag(response,flag);

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = us.getUserList();

        PrintJson.printJsonObj(response,userList);
    }

}

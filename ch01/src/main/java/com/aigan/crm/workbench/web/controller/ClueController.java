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
import com.aigan.crm.workbench.domain.ActivityRemark;
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
public class ClueController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到线索控制器");
        String path = request.getServletPath();
        if("/workbench/clue/xxx.do".equals(path)){
            //xxx(request,response);
        }else if("/workbench/clue/xxx.do".equals(path)){
            //xxx(request,response);
        }
    }



}

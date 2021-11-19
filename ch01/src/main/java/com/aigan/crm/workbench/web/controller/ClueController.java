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
import com.aigan.crm.workbench.domain.Clue;
import com.aigan.crm.workbench.domain.Tran;
import com.aigan.crm.workbench.service.ActivityService;
import com.aigan.crm.workbench.service.ClueService;
import com.aigan.crm.workbench.service.impl.ActivityServiceImpl;
import com.aigan.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
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
        if("/workbench/clue/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if("/workbench/clue/save.do".equals(path)){
            save(request,response);
        }else if("/workbench/clue/detail.do".equals(path)){
            detail(request,response);
        }else if("/workbench/clue/getActivityListByClueId.do".equals(path)){
            getActivityListByClueId(request,response);
        }else if("/workbench/clue/unbund.do".equals(path)){
            unbund(request,response);
        }else if("/workbench/clue/getActivityListByNameAndNoAssociateByClueId.do".equals(path)){
            getActivityListByNameAndNoAssociateByClueId(request,response);
        }else if("/workbench/clue/bund.do".equals(path)){
            bund(request,response);
        }else if("/workbench/clue/getActivityListByName.do".equals(path)){
            getActivityListByName(request,response);
        }else if("/workbench/clue/convert.do".equals(path)){
            convert(request,response);
        }
    }

    private void convert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("执行线索转换");

        String clueId = request.getParameter("clueId");

        // 接收是否需要创建交易的标记
        String flag = request.getParameter("flag");
        String createBy = ((User)request.getSession().getAttribute("user")).getName(); // if 有可能为空，但是 createBy 又是必要的

        Tran t = null;
        if("a".equals(flag)){
            t  = new Tran();

            // 接收表单中的参数
            String money = request.getParameter("money");
            String name = request.getParameter("name");
            String expectedDate = request.getParameter("expectedDate");
            String stage = request.getParameter("stage");
            String activityId = request.getParameter("activityId");
            String id = UUIDUtil.getUUID();

            String createTime = DateTimeUtil.getSysTime();


            t.setId(id);
            t.setName(name);
            t.setMoney(money);
            t.setExpectedDate(expectedDate);
            t.setStage(stage);
            t.setActivityId(activityId);
            t.setCreateBy(createBy);
            t.setCreateTime(createTime);
        }
        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag1 = cs.convert(clueId,t,createBy);

        if(flag1){
            response.sendRedirect(request.getContextPath() + "/workbench/clue/index.jsp");
        }

    }

    private void getActivityListByName(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据名称模糊查询市场活动列表");

        String aname = request.getParameter("aname");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> list = as.getActivityListByName(aname);

        PrintJson.printJsonObj(response,list);

    }

    private void bund(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行关联市场活动");
        String[] activityIds = request.getParameterValues("activityId");
        String clueId = request.getParameter("clueId");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = cs.bund(clueId,activityIds);

        PrintJson.printJsonFlag(response,flag);

    }

    private void getActivityListByNameAndNoAssociateByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("查询市场活动列表--根据名称模糊查询，排除已经关联的列表");

        String aname = request.getParameter("aname");
        String clueId = request.getParameter("clueId");

        Map<String,String> map = new HashMap<>();
        map.put("aname",aname);
        map.put("clueId",clueId);

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> list = as.getActivityListByNameAndNoAssociateByClueId(map);
        PrintJson.printJsonObj(response,list);

    }

    private void unbund(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行解除关联操作");

        String id = request.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = cs.unbund(id);

        PrintJson.printJsonFlag(response,flag);
    }

    private void getActivityListByClueId(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("根据线索id查询关联的市场活动列表");

        String clueId = request.getParameter("clueId");

        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        List<Activity> list = as.getActivityListByClueId(clueId);

        PrintJson.printJsonObj(response,list);
    }

    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("跳转到线索的详细信息页");

        String id = request.getParameter("id");

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        Clue c = cs.detail(id);

        request.setAttribute("c",c);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);

    }

    private void save(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("执行线索添加操作");

        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");


        Clue c = new Clue();
        c.setId(id);
        c.setFullname(fullname);
        c.setAppellation(appellation);
        c.setOwner(owner);
        c.setCompany(company);
        c.setJob(job);
        c.setEmail(email);
        c.setPhone(phone);
        c.setWebsite(website);
        c.setMphone(mphone);
        c.setState(state);
        c.setSource(source);
        c.setCreateBy(createBy);
        c.setCreateTime(createTime);
        c.setDescription(description);
        c.setContactSummary(contactSummary);
        c.setNextContactTime(nextContactTime);
        c.setAddress(address);

        ClueService cs = (ClueService) ServiceFactory.getService(new ClueServiceImpl());

        boolean flag = cs.save(c);

        PrintJson.printJsonFlag(response,flag);

    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得用户信息列表");

        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> uList = us.getUserList();

        PrintJson.printJsonObj(response,uList);
    }


}

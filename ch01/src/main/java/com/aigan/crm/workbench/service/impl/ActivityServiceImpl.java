package com.aigan.crm.workbench.service.impl;

import com.aigan.crm.utils.SqlSessionUtil;
import com.aigan.crm.vo.PaginationVO;
import com.aigan.crm.workbench.dao.ActivityDao;
import com.aigan.crm.workbench.domain.Activity;
import com.aigan.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

/**
 * @author aigan
 * @date 2021/10/26 23:06
 */
public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);


    @Override
    public boolean save(Activity a) {

        // 这里采用标记位 简化操作，实际肯定是不行的，应使用同UserController中自定义异常的方式
        boolean flag = true;
        int count = activityDao.save(a);

        if(count!=1) flag = false;

        return flag;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        // 获取total
        int total = activityDao.getTotalByCondition(map);

        // 获取dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);

        // 将total和dataList封装到VO中
        PaginationVO<Activity> vo = new PaginationVO<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }
}

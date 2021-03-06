package com.aigan.crm.workbench.service.impl;

import com.aigan.crm.settings.dao.UserDao;
import com.aigan.crm.settings.domain.User;
import com.aigan.crm.utils.SqlSessionUtil;
import com.aigan.crm.vo.PaginationVO;
import com.aigan.crm.workbench.dao.ActivityDao;
import com.aigan.crm.workbench.dao.ActivityRemarkDao;
import com.aigan.crm.workbench.domain.Activity;
import com.aigan.crm.workbench.domain.ActivityRemark;
import com.aigan.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author aigan
 * @date 2021/10/26 23:06
 */
public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public List<Activity> getActivityListByName(String aname) {

        List<Activity> list = activityDao.getActivityListByName(aname);

        return list;
    }

    @Override
    public List<Activity> getActivityListByNameAndNoAssociateByClueId(Map<String, String> map) {

        List<Activity> list = activityDao.getActivityListByNameAndNoAssociateByClueId(map);
        return list;
    }

    @Override
    public List<Activity> getActivityListByClueId(String clueId) {

        List<Activity> list = activityDao.getActivityListByClueId(clueId);

        return list;
    }

    @Override
    public boolean updateRemark(ActivityRemark ar) {
        boolean flag = true;

        int count = activityRemarkDao.updateRemark(ar);

        if(count != 1) flag = false;

        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark ar) {
        boolean flag  = true;

        int count = activityRemarkDao.saveRemark(ar);

        if(count != 1) flag = false;
        return flag;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = true;

        int count = activityRemarkDao.deleteRemarkById(id);

        if(count != 1) flag = false;

        return flag;
    }

    @Override
    public Activity detail(String id) {
        Activity a = activityDao.detail(id);
        return a;
    }

    @Override
    public List<ActivityRemark> getRemarkListByActivityId(String activityId) {
        List<ActivityRemark> arList = activityRemarkDao.getRemarkListByActivityId(activityId);
        return arList;
    }


    @Override
    public boolean save(Activity a) {

        // ????????????????????? ??????????????????????????????????????????????????????UserController???????????????????????????
        boolean flag = true;
        int count = activityDao.save(a);

        if(count!=1) flag = false;

        return flag;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        // ??????total
        int total = activityDao.getTotalByCondition(map);

        // ??????dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);

        // ???total???dataList?????????VO???
        PaginationVO<Activity> vo = new PaginationVO<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    @Override
    public Map<String, Object> getUserListAndActivity(String id) {

        // ???uList
        List<User> uList = userDao.getUserList();
        //???a
        Activity a = activityDao.getById(id);

        // ???uList???a?????????map
        Map<String,Object> map = new HashMap<>();
        map.put("uList",uList);
        map.put("a",a);

        // ??????map
        return map;
    }

    @Override
    public boolean update(Activity a) {
        boolean flag = true;
        int count = activityDao.update(a);

        if(count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean delete(String[] ids) {

        boolean flag = true;
        // ???????????????????????????????????????
        int count1 = activityRemarkDao.getCountByAids(ids);


        // ?????????????????????????????????????????????????????????????????????
        int count2 = activityRemarkDao.deleteByAids(ids);

        if(count1 != count2) flag = false;

        // ??????????????????
        int count3 = activityDao.delete(ids);
        if(count3 != ids.length) flag = false;

        return flag;
    }
}

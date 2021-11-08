package com.aigan.crm.workbench.service;

import com.aigan.crm.vo.PaginationVO;
import com.aigan.crm.workbench.domain.Activity;
import com.aigan.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

/**
 * @author aigan
 * @date 2021/10/26 23:05
 */
public interface ActivityService {
    boolean save(Activity a);

    PaginationVO<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity a);

    Activity detail(String id);

    List<ActivityRemark> getRemarkListByActivityId(String activityId);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark ar);

    boolean updateRemark(ActivityRemark ar);
}

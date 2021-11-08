package com.aigan.crm.workbench.dao;

import com.aigan.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * @author aigan
 * @date 2021/10/27 22:09
 */
public interface ActivityRemarkDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemarkListByActivityId(String activityId);

    int deleteRemarkById(String id);

    int saveRemark(ActivityRemark ar);

    int updateRemark(ActivityRemark ar);
}

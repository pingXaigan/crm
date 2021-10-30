package com.aigan.crm.workbench.dao;

import com.aigan.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * @author aigan
 * @date 2021/10/26 22:58
 */
public interface ActivityDao {
    int save(Activity a);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);
}

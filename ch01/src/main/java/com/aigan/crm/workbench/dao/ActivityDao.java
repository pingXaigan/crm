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

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity a);

    Activity detail(String id);

    List<Activity> getActivityListByClueId(String clueId);

    List<Activity> getActivityListByNameAndNoAssociateByClueId(Map<String, String> map);

    List<Activity> getActivityListByName(String aname);
}

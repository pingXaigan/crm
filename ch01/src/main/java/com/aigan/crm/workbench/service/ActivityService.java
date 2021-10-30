package com.aigan.crm.workbench.service;

import com.aigan.crm.vo.PaginationVO;
import com.aigan.crm.workbench.domain.Activity;

import java.util.Map;

/**
 * @author aigan
 * @date 2021/10/26 23:05
 */
public interface ActivityService {
    boolean save(Activity a);

    PaginationVO<Activity> pageList(Map<String, Object> map);
}

package com.aigan.crm.workbench.service.impl;

import com.aigan.crm.utils.SqlSessionUtil;
import com.aigan.crm.workbench.dao.ActivityDao;
import com.aigan.crm.workbench.service.ActivityService;

/**
 * @author aigan
 * @date 2021/10/26 23:06
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
}

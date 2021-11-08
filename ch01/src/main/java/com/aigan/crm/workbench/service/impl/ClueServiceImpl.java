package com.aigan.crm.workbench.service.impl;

import com.aigan.crm.utils.SqlSessionUtil;
import com.aigan.crm.workbench.dao.ClueDao;
import com.aigan.crm.workbench.service.ClueService;

/**
 * @author aigan
 * @date 2021/11/8 13:41
 */
public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
}

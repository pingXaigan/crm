package com.aigan.crm.workbench.service.impl;

import com.aigan.crm.utils.SqlSessionUtil;
import com.aigan.crm.workbench.dao.TranDao;
import com.aigan.crm.workbench.dao.TranHistoryDao;
import com.aigan.crm.workbench.service.TranService;

/**
 * @author aigan
 * @date 2021/11/20 15:22
 */
public class TranServiceImpl implements TranService {

    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

}

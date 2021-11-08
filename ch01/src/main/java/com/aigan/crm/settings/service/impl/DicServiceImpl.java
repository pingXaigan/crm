package com.aigan.crm.settings.service.impl;

import com.aigan.crm.settings.dao.DicTypeDao;
import com.aigan.crm.settings.dao.DicValueDao;
import com.aigan.crm.settings.service.DicService;
import com.aigan.crm.utils.SqlSessionUtil;

/**
 * @author aigan
 * @date 2021/11/8 18:11
 */
public class DicServiceImpl implements DicService {

    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);


}

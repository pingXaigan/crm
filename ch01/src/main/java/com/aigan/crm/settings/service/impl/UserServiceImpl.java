package com.aigan.crm.settings.service.impl;

import com.aigan.crm.settings.dao.UserDao;
import com.aigan.crm.settings.service.UserService;
import com.aigan.crm.utils.SqlSessionUtil;

/**
 * @author aigan
 * @date 2021/10/19 21:32
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

}

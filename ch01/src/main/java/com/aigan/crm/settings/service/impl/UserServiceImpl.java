package com.aigan.crm.settings.service.impl;

import com.aigan.crm.exception.LoginException;
import com.aigan.crm.settings.dao.UserDao;
import com.aigan.crm.settings.domain.User;
import com.aigan.crm.settings.service.UserService;
import com.aigan.crm.utils.DateTimeUtil;
import com.aigan.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author aigan
 * @date 2021/10/19 21:32
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);


    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException{
        Map<String, String> map = new HashMap<String, String>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);

        User user = userDao.login(map);

        if(user == null) throw new LoginException("账号密码有误");

        // 程序执行到此，账密正确，需要向下验证其他的信息

        // 验证失效时间
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currentTime) < 0) throw new LoginException("账号失效");

        // 判断锁定状态
        String lockState = user.getLockState();
        if("0".equals(lockState)) throw new LoginException("账号已锁定，联系小艾解锁");

        // 判断IP地址
        String allowIps = user.getAllowIps();
        if(!allowIps.contains(ip)) throw new LoginException("IP已禁止，联系小艾解禁");

        return user;
    }
}

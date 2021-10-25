package com.aigan.crm.settings.service;

import com.aigan.crm.exception.LoginException;
import com.aigan.crm.settings.domain.User;

/**
 * @author aigan
 * @date 2021/10/19 21:30
 */
public interface UserService {
    User login(String loginAct, String loginPwd, String ip) throws LoginException;
}

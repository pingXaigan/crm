package com.aigan.crm.settings.dao;

import com.aigan.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @author aigan
 * @date 2021/10/19 21:26
 */
public interface UserDao {

    User login(Map<String, String> map);

    List<User> getUserList();
}

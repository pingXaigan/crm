package com.aigan.crm.workbench.service.impl;

import com.aigan.crm.utils.SqlSessionUtil;
import com.aigan.crm.workbench.dao.CustomerDao;
import com.aigan.crm.workbench.service.CustomerService;

import java.util.List;

/**
 * @author aigan
 * @date 2021/11/22 16:14
 */
public class CustomerServiceImpl implements CustomerService {
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    @Override
    public List<String> getCustomerName(String name) {

        List<String> list = customerDao.getCustomerName(name);
        return list;
    }
}

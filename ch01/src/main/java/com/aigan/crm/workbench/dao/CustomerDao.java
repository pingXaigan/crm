package com.aigan.crm.workbench.dao;

import com.aigan.crm.workbench.domain.Customer;

public interface CustomerDao {

    Customer getCustomerByName(String company);

    int save(Customer customer);
}

package com.aigan.crm.settings.dao;

import com.aigan.crm.settings.domain.DicValue;

import java.util.List;

/**
 * @author aigan
 * @date 2021/11/8 18:08
 */
public interface DicValueDao {
    List<DicValue> getListByCode(String code);
}

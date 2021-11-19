package com.aigan.crm.settings.dao;

import com.aigan.crm.settings.domain.DicType;

import java.util.List;

/**
 * @author aigan
 * @date 2021/11/8 18:07
 */
public interface DicTypeDao {
    List<DicType> getTypeList();
}

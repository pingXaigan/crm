package com.aigan.crm.settings.service.impl;

import com.aigan.crm.settings.dao.DicTypeDao;
import com.aigan.crm.settings.dao.DicValueDao;
import com.aigan.crm.settings.domain.DicType;
import com.aigan.crm.settings.domain.DicValue;
import com.aigan.crm.settings.service.DicService;
import com.aigan.crm.utils.SqlSessionUtil;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author aigan
 * @date 2021/11/8 18:11
 */
public class DicServiceImpl implements DicService {

    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public Map<String, List<DicValue>> getAll() {

        Map<String, List<DicValue>> pMap = new HashMap<>();
        // 将字典类型取出
        List<DicType> dtList = dicTypeDao.getTypeList();

        // 将字典类型遍历
        for(DicType dt : dtList){
            String code = dt.getCode();

            // 根据每一种类型来取得字典值列表
            List<DicValue> dvList = dicValueDao.getListByCode(code);

            pMap.put(code + "List" , dvList);
        }

        return pMap;
    }
}

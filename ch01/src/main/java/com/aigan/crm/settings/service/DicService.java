package com.aigan.crm.settings.service;

import com.aigan.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

/**
 * @author aigan
 * @date 2021/11/8 18:10
 */
public interface DicService {
    Map<String, List<DicValue>> getAll();
}

package com.aigan.crm.workbench.service;

import com.aigan.crm.workbench.domain.Clue;
import com.aigan.crm.workbench.domain.Tran;

/**
 * @author aigan
 * @date 2021/11/8 13:40
 */
public interface ClueService {

    boolean save(Clue c);

    Clue detail(String id);

    boolean unbund(String id);

    boolean bund(String clueId, String[] activityIds);


    boolean convert(String clueId, Tran t, String createBy);
}

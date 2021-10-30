package com.aigan.crm.vo;

import java.util.List;

/**
 * @author aigan
 * @date 2021/10/29 16:24
 */

/**
 * 分页查询有很多模块都要用到，所以选择封装成VO
 * @param <T>
 */
public class PaginationVO<T> {


    private int total;
    private List<T> dataList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}

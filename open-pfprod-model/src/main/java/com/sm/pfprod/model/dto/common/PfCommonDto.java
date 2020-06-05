package com.sm.pfprod.model.dto.common;

import java.io.Serializable;

public class PfCommonDto implements Serializable {

    private static final long serialVersionUID = -7400624365266677449L;
    /**
     * 每页记录数
     */
    private Integer pageSize;
    /**
     * 当前页
     */
    private Integer pageNum;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

}

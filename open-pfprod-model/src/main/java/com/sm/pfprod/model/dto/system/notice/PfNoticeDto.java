package com.sm.pfprod.model.dto.system.notice;

import com.sm.pfprod.model.param.PageParam;

import java.io.Serializable;

public class PfNoticeDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -8065165575922718762L;

    private String publishTimeBegin;
    private String publishTimeEnd;

    public String getPublishTimeBegin() {
        return publishTimeBegin;
    }

    public void setPublishTimeBegin(String publishTimeBegin) {
        this.publishTimeBegin = publishTimeBegin;
    }

    public String getPublishTimeEnd() {
        return publishTimeEnd;
    }

    public void setPublishTimeEnd(String publishTimeEnd) {
        this.publishTimeEnd = publishTimeEnd;
    }
}

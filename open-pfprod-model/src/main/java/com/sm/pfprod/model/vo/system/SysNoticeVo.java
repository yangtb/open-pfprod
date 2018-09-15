package com.sm.pfprod.model.vo.system;

import java.io.Serializable;
import java.util.Date;

public class SysNoticeVo implements Serializable {

    private static final long serialVersionUID = -3457933153204854464L;

    private Long        id;                 // 主键
    private String      noticeTitle;        // 公告标题
    private String      noticeType;         // 公告类型 1=系统公告2=活动公告
    private String      noticeContent;      // 公告内容
    private String      operator;            // 创建人ID
    private String      creatorName;        // 创建人
    private Date        gmtCreate;          // 创建时间
    private Date        gmtUpdate;          // 更新时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
}

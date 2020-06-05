package com.sm.pfprod.model.entity;

import java.io.Serializable;
import java.util.Date;

public class SysAuthority implements Serializable {

    private static final long serialVersionUID = -5969072684531040902L;

    private Long        authorityId;       // 权限资源主键id
    private String      authorityName;     // 权限资源名称、代号，存储URL
    private int         authorityType;     // 权限资源类型 ，1为url资源权限，2为菜单资源权限
    private String      description;       // 资源接口描述
    private Date        gmtCreate;         // 创建时间
    private Date        gmtUpdate;         // 最后更新时间
    private String      remark;            // 接口备注

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public int getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(int authorityType) {
        this.authorityType = authorityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

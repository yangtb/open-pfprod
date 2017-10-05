package com.sm.pfprod.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 */
public class SysRole implements Serializable {

    private static final long serialVersionUID = -4292388147414854049L;

    private Long        roleId;         // 角色ID
    private String      name;           // 角色名称
    private String      resume;         // 角色描述
    private int         state;          // 是否有效(0-有效 1-无效)
    private String      isDeleted;      // 删除标示，N未删除 Y-已删除
    private Long        creatorId;      // 创建人ID
    private Date        gmtCreate;      // 创建时间
    private Date        gmtUpdate;      // 更新时间

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
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

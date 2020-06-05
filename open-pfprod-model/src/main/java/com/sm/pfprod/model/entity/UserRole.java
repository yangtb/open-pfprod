package com.sm.pfprod.model.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class UserRole implements Serializable {

    private static final long serialVersionUID = -2433082013255714801L;
    private Long        id;         // 主键
    @JSONField(name = "user_id")
    private Long        userId;     // 用户ID
    @JSONField(name = "role_id")
    private Long        roleId;     // 角色ID
    @JSONField(name = "is_deleted")
    private String      isDeleted;  // 删除标示，N未删除 Y-已删除
    @JSONField(name = "gmt_create")
    private Date        gmtCreate;  // 创建时间
    @JSONField(name = "gmt_modify")
    private Date        gmtModify;  // 更新时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }
}

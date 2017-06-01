package com.sm.pfprod.model.entity;

import java.io.Serializable;
import java.util.Date;

public class SysRoleAuthorityRef implements Serializable {

    private static final long serialVersionUID = 7455796078372374148L;

    private Long id;            // 主键id
    private Long authorityId;   // 权限资源id
    private Long roleId;        // 资源所属的角色id
    private Date gmtCreate;     // 创建时间
    private Date gmtUpdate;     // 最后更新时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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

package com.sm.pfprod.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色菜单权限关联表
 */
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 3078552317142157942L;

    private Long    roleMenuId;     // 主键
    private Long    roleId;         // 角色ID
    private Long    menuId;         // 权限ID
    private Date    gmtCreate;      // 创建时间
    private Date    gmtModify;      // 更新时间
    private String  isDeleted;      // 删除标示，N未删除 Y-已删除

    public Long getRoleMenuId() {
        return roleMenuId;
    }

    public void setRoleMenuId(Long roleMenuId) {
        this.roleMenuId = roleMenuId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
}

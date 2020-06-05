package com.sm.pfprod.model.entity;

import java.io.Serializable;

/**
 * 角色按钮权限
 */
public class SysRoleButton implements Serializable {

    private static final long serialVersionUID = -4780043199264926956L;

    private Long    roleId;         // 角色ID
    private Long    menuButtonId;   // 角色按钮ID

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuButtonId() {
        return menuButtonId;
    }

    public void setMenuButtonId(Long menuButtonId) {
        this.menuButtonId = menuButtonId;
    }
}

package com.sm.pfprod.model.dto.user.menu;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class MenuDto implements Serializable {

    private static final long serialVersionUID = 1699729232949638650L;

    @JSONField(name = "parent_id")
    private Long        parentId;   // 父菜单id

    @JSONField(name = "menu_id")
    private Long        menuId;     // 菜单ID

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}

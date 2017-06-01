package com.sm.pfprod.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单按钮表
 */
public class SysMenuButton implements Serializable {

    private static final long serialVersionUID = -986150631800387874L;

    private Long        menuButtonId;   // 菜单按钮id
    private Long        menuId;         // 菜单ID
    private String      buttonCode;     // 按钮代码
    private String      buttonName;     // 按钮名称
    private Date        gmtCreate;      // 创建时间
    private Date        gmtUpdate;      // 更新时间
    private String      isDeleted;      // 删除标示，N未删除 Y-已删除

    public Long getMenuButtonId() {
        return menuButtonId;
    }

    public void setMenuButtonId(Long menuButtonId) {
        this.menuButtonId = menuButtonId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getButtonCode() {
        return buttonCode;
    }

    public void setButtonCode(String buttonCode) {
        this.buttonCode = buttonCode;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
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

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
}

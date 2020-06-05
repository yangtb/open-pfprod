package com.sm.pfprod.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单表
 */
public class SysMenu implements Serializable {

    private static final long serialVersionUID = -7847458250447175878L;

    private Long        menuId;     // 菜单ID
    private Integer     level;      // 菜单等级从1开始
    private String      url;        // 菜单url
    private String      name;       // 菜单名称
    private Long        parentId;   // 父菜单id
    private int         sort;       // 排序(越大越前)
    private int         disable;    // 是否有效(0-有效 1-无效)
    private String      isDeleted;  // 删除标示，N未删除 Y-已删除
    private Date        gmtCreate;  // 创建时间
    private Date        gmtModify;  // 更新时间
    private String      img;        // 图片

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getDisable() {
        return disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

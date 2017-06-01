package com.sm.pfprod.model.vo.menu;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class PfBaseMenuVo implements Serializable {

    private static final long serialVersionUID = 3193336837884976266L;

    @JSONField(name = "menu_id")
    private Long            menuId;     // 菜单ID
    private String          url;        // 菜单url
    private String          name;       // 菜单名称
    private String          img;        // 图片

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

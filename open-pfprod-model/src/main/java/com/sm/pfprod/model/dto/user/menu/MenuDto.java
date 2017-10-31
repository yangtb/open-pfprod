package com.sm.pfprod.model.dto.user.menu;

import com.sm.pfprod.model.param.PageParam;

import java.io.Serializable;

public class MenuDto extends PageParam implements Serializable {

    private static final long serialVersionUID = 1699729232949638650L;

    private Long        id;         // 菜单ID
    private String      name;       // 菜单名称
    private Integer     level;      // 菜单级别
    private String      status;     // 菜单状态

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

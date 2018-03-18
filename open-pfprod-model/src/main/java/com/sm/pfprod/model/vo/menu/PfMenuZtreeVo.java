package com.sm.pfprod.model.vo.menu;

import java.io.Serializable;

public class PfMenuZtreeVo implements Serializable {

    private static final long serialVersionUID = -4901737093643963963L;

    private Long        menuId;     // 菜单id
    private String      id;
    private String      pId;
    private String      name;
    private boolean     checked;
    private boolean     open;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}

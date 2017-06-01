package com.sm.pfprod.model.enums;

/**
 * 角色枚举
 */
public enum AdminRoleType {
    NORMAL("2", "普通用户"),
    SUNPER("1", "超级管理员");

    private String value;
    private String desc;

    AdminRoleType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

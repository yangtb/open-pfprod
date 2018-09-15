package com.sm.pfprod.model.enums;

/**
 * @ClassName: SysEnum
 * @Description: 系统枚举
 * @Author yangtongbin
 * @Date 2017/10/10 15:47
 */
public enum SysEnum {

    DICTIONARY_GROUP("DICTIONARY_GROUP", "字典分组");

    private String code;
    private String desc;

    private SysEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

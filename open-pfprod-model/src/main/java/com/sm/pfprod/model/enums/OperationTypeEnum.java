package com.sm.pfprod.model.enums;

/**
 * @ClassName: OperationTypeEnum
 * @Description: 操作类型枚举
 * @Author yangtongbin
 * @Date 2018/10/11
 */
public enum OperationTypeEnum {

    DEL("del", "删除"),
    UPDATE_STATUS("updateStatus", "修改状态");

    private String code;
    private String desc;

    private OperationTypeEnum(String code, String desc) {
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

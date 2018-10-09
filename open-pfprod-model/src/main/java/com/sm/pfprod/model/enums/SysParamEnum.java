package com.sm.pfprod.model.enums;

/**
 * @ClassName: SysParamEnum
 * @Description: 系统参数枚举
 * @Author yangtongbin
 * @Date 2018/9/25
 */
public enum SysParamEnum {

    ORG_TRIAL_SWITCH("orgTrialSwitch", "是否开启机构试用"),
    ORG_EXPIRY_DAY("orgExpiryDay", "机构试用有效期"),
    VISITOR_SWITCH("visitorSwitch","是否开启游客功能");

    private String code;
    private String desc;

    private SysParamEnum(String code, String desc) {
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

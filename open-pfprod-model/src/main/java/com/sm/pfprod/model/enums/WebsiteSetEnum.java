package com.sm.pfprod.model.enums;

/**
 * @ClassName: WebsiteSetEnum
 * @Description: 网站参数枚举
 * @Author yangtongbin
 * @Date 2018/9/19 09:26
 */
public enum WebsiteSetEnum {

    NAME("website.name", "网站名称"),
    LOG_SWITCH("website.logSwitch", "日志开关"),
    UPLOAD_TYPE("website.uploadType", "上传类型"),
    COPYRIGHT("website.copyright", "版权信息"),
    APPROVE("website.approve", "网站备案号"),
    MAX_UPLOAD_VALUE("website.maxUploadValue", "最大上传值"),
    IP_BLACKLIST("website.ipBlacklist", "IP黑名单");

    private String code;
    private String desc;

    private WebsiteSetEnum(String code, String desc) {
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

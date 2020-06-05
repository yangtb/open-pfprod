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
    PIC_UPLOAD_TYPE("website.pic.uploadType", "图片上传类型"),
    PIC_MAX_UPLOAD_VALUE("website.pic.maxUploadValue", "图片最大上传值"),
    AUDIO_UPLOAD_TYPE("website.audio.uploadType", "音频上传类型"),
    AUDIO_MAX_UPLOAD_VALUE("website.audio.maxUploadValue", "音频最大上传值"),
    VIDEO_UPLOAD_TYPE("website.video.uploadType", "视频上传类型"),
    VIDEO_MAX_UPLOAD_VALUE("website.video.maxUploadValue", "视频最大上传值"),
    COPYRIGHT("website.copyright", "版权信息"),
    APPROVE("website.approve", "网站备案号"),
    IP_BLACKLIST("website.ipBlacklist", "IP黑名单");

    private String code;
    private String desc;

    WebsiteSetEnum(String code, String desc) {
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

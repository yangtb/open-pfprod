package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @ClassName: PfEmailSetDto
 * @Description: 网站基本参数设置
 * @Author yangtongbin
 * @Date 2018/9/16 16:26
 */
@Setter
@Getter
@ToString
public class PfWebsiteSet implements Serializable {

    private static final long serialVersionUID = -7816276116627400687L;

    /**
     * 网站名称
     */
    private String name;
    /**
     * 日志开关
     */
    private String logSwitch;
    /**
     * 图片-上传类型
     */
    private String picUploadType;
    /**
     * 图片-最大上传值
     */
    private String picMaxUploadValue;
    /**
     * 音频-上传类型
     */
    private String audioUploadType;
    /**
     * 音频-最大上传值
     */
    private String audioMaxUploadValue;
    /**
     * 视频-上传类型
     */
    private String videoUploadType;
    /**
     * 视频-最大上传值
     */
    private String videoMaxUploadValue;
    /**
     * 版权信息
     */
    private String copyright;
    /**
     * 网站备案号
     */
    private String approve;
    /**
     * IP黑名单
     */
    private String ipBlacklist;
}

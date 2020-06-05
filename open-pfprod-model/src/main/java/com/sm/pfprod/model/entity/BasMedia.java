package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.*;

/**
 * 基础_多媒体
 *
 * @author author
 */
@Setter
@Getter
@ToString
public class BasMedia implements Serializable {

    private static final long serialVersionUID = 1538900072127L;


    /**
     * 主键
     * 多媒体ID
     */
    private Long idMedia;

    /**
     * 1 图片 2 音频 3 视频 4 其它
     */
    private String sdType;

    /**
     * 描述
     */
    private String des;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件格式
     */
    private String format;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 0 未激活 1 已激活
     */
    private String fgActive;

    /**
     * 0 正常，1 删除
     */
    private String fgValid;

    /**
     * 创建人员
     */
    private String creator;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改人员
     */
    private String operator;

    /**
     * 修改时间
     */
    private Date gmtModify;


}

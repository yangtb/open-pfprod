package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础_病历_组件定义
 *
 * @author author
 */
@Setter
@Getter
@ToString
public class BasMedAsse implements Serializable {

    private static final long serialVersionUID = 1539331892017L;

    /**
     * 主键
     * 病历组件id
     */
    private Long idMedAsse;

    /**
     * 病历组件编码
     */
    private String cdMedAsse;

    /**
     * 病历组件名称
     */
    private String name;

    /**
     * 病历组件描述
     */
    private String descript;

    /**
     * 1x 正常代码中写死 11. 普通 2x 页面嵌入 21. 页签嵌入
     */
    private String sdMedAsse;

    /**
     * 病历组件嵌入代码
     */
    private String script;

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

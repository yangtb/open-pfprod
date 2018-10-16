package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础_模板_评估标签
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class BasEvaTag implements Serializable {

    private static final long serialVersionUID = 1539434496473L;

    /**
     * 主键
     * 模板标签id
     */
    private Long idTag;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签logo
     */
    private Long idMedia;

    /**
     * 模板id
     */
    private Long idDemo;

    /**
     * 评估组件编码
     */
    private String cdEvaAsse;

    /**
     * 制作病历时是否显示
     */
    private String fgShowMake;

    /**
     * 执行时是否显示
     */
    private String fgShowExec;

    /**
     * 0 未激活 1 已激活
     */
    private String fgActive;

    /**
     * 0 正常，1 删除
     */
    private String fgValid;

    /**
     * 排序
     */
    private Integer sort;

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

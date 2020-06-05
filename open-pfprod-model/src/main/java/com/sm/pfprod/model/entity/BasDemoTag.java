package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础_模板标签
 *
 * @author author
 */
@Setter
@Getter
@ToString
public class BasDemoTag implements Serializable {

    private static final long serialVersionUID = 1539001011477L;

    /**
     * 主键
     * 标签ID
     */
    private Long idTag;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 所属模板
     */
    private Long idDemo;

    /**
     * 1. 文本 2. 图片 3. 患者信息 4. 问诊 5. 检查 6. 检验
     */
    private String sdTag;

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

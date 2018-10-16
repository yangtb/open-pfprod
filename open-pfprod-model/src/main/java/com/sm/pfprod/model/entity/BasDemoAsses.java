package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础_模板评估标准
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class BasDemoAsses implements Serializable {

    private static final long serialVersionUID = 1539572725764L;


    /**
     * 主键
     * 评估维度id
     */
    private Long idDimemsion;

    /**
     * 维度名称
     */
    private String name;

    /**
     * 父维度
     */
    private Long parDimemsion;

    /**
     * 模板id
     */
    private Long idDemo;

    /**
     * 权重
     */
    private Integer weight;

    /**
     * 系统计算标志
     */
    private String fgSystemAlgorithm;

    /**
     * 系统计算_分值算法
     */
    private Long idAlgorithm;

    /**
     * 非系统计算_分值上限
     */
    private Integer scoreUpper;

    /**
     * 非系统计算_分值下限
     */
    private Integer scoreLower;

    /**
     * 非系统计算_默认分值
     */
    private Integer scoreDefault;

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

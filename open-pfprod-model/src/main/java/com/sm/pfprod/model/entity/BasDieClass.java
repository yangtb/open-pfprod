package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: BasDieClass
 * @Description: 疾病目录
 * @Author yangtongbin
 * @Date 2018/9/29
 */
@Setter
@Getter
@ToString
public class BasDieClass implements Serializable {

    private static final long serialVersionUID = 1538210495215L;

    /**
     * 主键
     * 疾病目录ID
     */
    private Long idDieclass;

    /**
     * 疾病目录名称
     */
    private String name;

    /**
     * 目录编码
     */
    private String cd;

    /**
     * 上级编码
     */
    private String cdPar;

    /**
     * ICD编码_起
     */
    private String icdB;

    /**
     * ICD编码_终
     */
    private String icdE;

    /**
     * 激活标志
     */
    private String fgActive;

    /**
     * 删除标志
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
     * isNullAble:0
     */
    private String operator;

    /**
     * 修改时间
     */
    private Date gmtModify;

}

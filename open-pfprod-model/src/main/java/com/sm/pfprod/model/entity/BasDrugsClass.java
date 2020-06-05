package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 药品目录
 *
 * @author author
 */
@Setter
@Getter
@ToString
public class BasDrugsClass implements Serializable {

    private static final long serialVersionUID = 1538210523980L;

    /**
     * 主键
     * 药品目录ID
     */
    private Long idDrugsclass;

    /**
     * 目录名称
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
     */
    private String operator;

    /**
     * 修改时间
     */
    private Date gmtModify;
}

package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.*;

/**
 * 基础_检验项目
 *
 * @author author
 */
@Setter
@Getter
@ToString
public class BasInspectItem implements Serializable {

    private static final long serialVersionUID = 1538920281012L;

    /**
     * 主键
     * 项目ID
     */
    private Long idInspectItem;

    /**
     * 项目名称
     */
    private String naItem;

    /**
     * 检验分类ID
     */
    private Long idInspect;

    /**
     * 英文缩写
     */
    private String naShort;

    /**
     * 标准值
     */
    private String desStand;

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

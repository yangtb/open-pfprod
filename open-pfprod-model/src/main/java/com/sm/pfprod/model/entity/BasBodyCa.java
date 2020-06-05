package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.*;

/**
 * 基础_检查分类
 *
 * @author author
 */
@Setter
@Getter
@ToString
public class BasBodyCa implements Serializable {

    private static final long serialVersionUID = 1538920229280L;

    /**
     * 主键
     * 分类ID
     */
    private Long idBodyCa;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级分类ID
     */
    private String idPar;

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

package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 班级
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class SysClass implements Serializable {

    private static final long serialVersionUID = 1541221602549L;


    /**
     * 主键
     * 班级id
     */
    private Long idClass;

    /**
     * 班级名称
     */
    private String name;

    /**
     * 上级组织
     */
    private Long idPar;

    /**
     * 所属机构
     */
    private Long idOrg;

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

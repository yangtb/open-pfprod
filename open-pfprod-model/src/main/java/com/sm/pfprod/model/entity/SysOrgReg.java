package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@ToString
public class SysOrgReg implements Serializable {

    /**
     * 申请ID
     */
    private Long idReg;
    /**
     * 机构id
     */
    private Long idOrg;

    /**
     * 状态 0=未处理, 1=审核通过, 2=审核驳回
     */
    private String sdReg;

    /**
     * 申请人
     */
    private String applyor;

    /**
     * 申请时间
     */
    private Date gmtApply;
    /**
     * 处理人
     */
    private String confirmor;

    /**
     * 处理时间
     */
    private Date gmtConfirm;

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


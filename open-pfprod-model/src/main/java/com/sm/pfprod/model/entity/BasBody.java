package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础_体检部位
 *
 * @author author
 */
@Setter
@Getter
@ToString
public class BasBody implements Serializable {

    private static final long serialVersionUID = 1538920225605L;

    /**
     * 主键
     * 部位ID
     */
    private Long idBody;

    /**
     * 部位描述
     */
    private String desBody;

    /**
     * 1. 正面 2. 背面 3. 胸部 4. 腹部 5. 头部
     */
    private String sdBody;

    /**
     * 部位分类ID
     */
    private Long idBodyCa;

    /**
     * 1. 触诊 2. 听诊 3. 视诊 4. 叩诊 5. 设备
     */
    private String cdCheck;

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

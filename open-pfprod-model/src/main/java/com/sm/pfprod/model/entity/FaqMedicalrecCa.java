package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 知识库_病例分类
 *
 * @author author
 */
@Setter
@Getter
@ToString
public class FaqMedicalrecCa implements Serializable {

    private static final long serialVersionUID = 1539141894021L;

    /**
     * 主键
     * 分类id
     */
    private Long idMedicalrecCa;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级分类id
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

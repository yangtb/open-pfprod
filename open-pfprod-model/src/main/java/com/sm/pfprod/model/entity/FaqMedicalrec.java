package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 知识库_病历
 *
 * @author author
 */
@Setter
@Getter
@ToString
public class FaqMedicalrec implements Serializable {

    private static final long serialVersionUID = 1539141898553L;

    /**
     * 主键
     * 病历id
     */
    private Long idMedicalrec;

    /**
     * 病历名称
     */
    private String name;

    /**
     * 模板id
     */
    private Long idDemo;

    /**
     * 所属分类
     */
    private Long idMedicalrecCa;

    /**
     * 病历归属
     */
    private Long idOrg;

    /**
     * 病历级别 : 1 初级 2 中级 3 高级
     */
    private String sdLevel;

    /**
     * 病历用途 : 1 训练病历 2 测试病历
     */
    private String sdUse;

    /**
     * 使用次数
     */
    private Integer count;

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

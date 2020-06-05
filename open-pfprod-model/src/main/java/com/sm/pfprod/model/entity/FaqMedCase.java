package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 知识库_病例_组件案例
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class FaqMedCase implements Serializable {

    private static final long serialVersionUID = 1539746308210L;

    /**
     * 主键
     * 病例组件案例id
     */
    private Long idMedCase;

    /**
     * 案例名称
     */
    private String name;

    /**
     * 案例描述
     */
    private String descript;

    /**
     * 病例组件编码
     */
    private String cdMedAsse;

    /**
     * 组件归属
     */
    private Long idOrg;

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

package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 知识库_评估_组件案例
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class FaqEvaCase implements Serializable {

    private static final long serialVersionUID = 1540188134441L;

    /**
     * 主键
     * 评估组件案例id
     */
    private Long idEvaCase;

    /**
     * 评估组件编码
     */
    private String cdEvaAsse;

    /**
     * 案例名称
     */
    private String name;

    /**
     * 案例描述
     */
    private String descript;

    /**
     * 等效评估标志
     */
    private String fgGroup;

    /**
     * 评估项上限分值
     */
    private Integer scoreUpper;

    /**
     * 评估项下限分值
     */
    private Integer scoreLower;

    /**
     * 评估项默认分值
     */
    private Integer scoreDefault;

    /**
     * 组件归属
     */
    private Long idOrg;

    /**
     * 机构名称
     */
    private String orgName;

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
     * isNullAble:0
     */
    private String operator;

    /**
     * 修改时间
     * isNullAble:0
     */
    private Date gmtModify;

}

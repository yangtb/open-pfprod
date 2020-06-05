package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础_病例_评估表定义
 *
 * @author author
 */
@Setter
@Getter
@ToString
public class BasEvaAsse implements Serializable {

    private static final long serialVersionUID = 1539331938873L;

    /**
     * 主键
     * 评估组件id
     */
    private Long idEvaAsse;

    /**
     * 评估组件编码
     */
    private String cdEvaAsse;

    /**
     * 评估组件名称
     */
    private String name;

    /**
     * 评估组件描述
     */
    private String descript;

    /**
     * 1x 正常代码中写死 11. 普通 2x 页面嵌入 21. 页签嵌入
     */
    private String sdEvaAsse;

    /**
     * 评估组件嵌入代码
     */
    private String script;

    /**
     * 嵌入代码_病例执行
     */
    private String scriptExec;

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

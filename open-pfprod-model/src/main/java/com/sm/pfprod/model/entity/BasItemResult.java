package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 基础_检验结果
 *
 * @author author
 */
@Setter
@Getter
@ToString
public class BasItemResult implements Serializable {

    private static final long serialVersionUID = 1538920283902L;

    /**
     * 主键
     * 结果ID
     */
    private Long idResult;

    /**
     * 结果值
     */
    private String valResult;

    /**
     * 结果说明
     */
    private String desResult;

    /**
     * 项目ID
     */
    private Long idInspectItem;

    /**
     * 多媒体ID
     */
    private String idMedia;

    /**
     * 费用
     */
    private BigDecimal costMoney;

    /**
     * 消耗时间
     */
    private BigDecimal costTime;

    /**
     * 结果是否在医嘱前展示
     */
    private String fgShow;

    /**
     * 是否需要说明理由
     */
    private String fgReason;

    /**
     * 是否根据病人回答反馈
     */
    private String fgBack;

    /**
     * 默认答案
     */
    private String fgDefault;

    /**
     * 专家解读
     */
    private String desExpert;

    /**
     * 标签
     */
    private String fgTag;

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

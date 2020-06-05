package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 知识库_检验_检验明细
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class FaqMedCaseInspectList implements Serializable {

    private static final long serialVersionUID = 1540204457337L;

    /**
     * 主键
     * 明细id
     */
    private Long idMedCaseList;

    /**
     * 病例组件案例id
     */
    private Long idMedCase;

    /**
     * 检验分类id
     */
    private Long idInspect;

    /**
     * 项目id
     */
    private Long idInspectItem;

    /**
     * 项目名称
     */
    private String naItem;

    /**
     * 英文缩写
     */
    private String naShort;

    /**
     * 标准值
     */
    private String desStand;

    /**
     * 结果id
     */
    private Long idResult;

    /**
     * 结果值
     */
    private String valResult;

    /**
     * 结果描述
     */
    private String desResult;

    /**
     * 多媒体id
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
     * 专家解读
     */
    private String desExpert;

    /**
     * 重载标志
     */
    private String fgCarried;

    /**
     * 是否阳性
     */
    private String isMasculine;

}

package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 知识库_问诊_问题明细
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class FaqMedCaseInquesList implements Serializable {

    private static final long serialVersionUID = 1539933191243L;

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
     * 问诊分类id
     */
    private Long idInquesCa;

    /**
     * 问题id
     */
    private Long idInques;

    /**
     * 问题内容
     */
    private String desInques;

    /**
     * 答案id
     */
    private Long idAnswer;

    /**
     * 答案内容
     */
    private String desAnswer;

    /**
     * 是否阳性
     */
    private String isMasculine;

    /**
     * 多媒体id
     */
    private Long idMedia;

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
     * 问诊标签
     */
    private String sdInquesLabel;

    private Long idInquesPre;
    private Long idInquesPre2;
    private Long idInquesPre3;
    private Long idInquesPre4;
    private Long idInquesPre5;

}

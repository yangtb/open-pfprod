package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 评估日志
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmEvaLog implements Serializable {

    private static final long serialVersionUID = 1542552419272L;


    /**
     * 主键
     */
    private Long idEvaLog;

    /**
     * 病例评估维度ID
     */
    private Long idTestexecResultDimension;

    /**
     * 评估项名称
     */
    private String nameEva;

    /**
     *
     */
    private String nameResult;

    /**
     * 评估项标准分值
     */
    private BigDecimal scoreEva;

    /**
     * 评估项得分
     */
    private BigDecimal scoreResult;

    /**
     * 评估描述
     */
    private String desLog;

}

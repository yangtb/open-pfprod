package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 临床模拟_测试执行_病历结果_诊断_确诊理由
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmMedResultDieReason implements Serializable {

    private static final long serialVersionUID = 1542177157354L;

    /**
     * 主键
     */
    private Long idDieReason;

    /**
     * 病历结果诊断ID
     */
    private Long idTestexecResultDiagnosis;

    /**
     * 确诊理由阶段
     */
    private String sdEvaEffciency;

    /**
     * 明细id
     */
    private Long idMedCaseList;

    /**
     * 明细text
     */
    private String idMedCaseListText;

    /**
     * 0 正常，1 删除
     */
    private String fgValid;

    /**
     * 创建时间
     */
    private Date gmtCreate;

}

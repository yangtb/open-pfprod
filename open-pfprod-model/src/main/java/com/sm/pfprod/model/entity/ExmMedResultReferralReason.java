package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 添加理由、排除理由
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmMedResultReferralReason implements Serializable {

    private static final long serialVersionUID = 1544091676842L;

    /**
     * 主键
     * 病例结果拟诊理由ID
     */
    private Long idReferralReason;

    /**
     * 病历结果拟诊ID
     */
    private Long idTestexecResultReferral;

    /**
     * 排除标志
     */
    private String fgExclude;

    /**
     * 理由评估阶段
     */
    private String sdEvaEffciency;

    /**
     * 问诊ID
     */
    private Long idInques;

    /**
     * 检查ID
     */
    private Long idBody;

    /**
     * 检验ID
     */
    private Long idInspectItem;

    /**
     * 明细ID
     */
    private Long idMedCaseList;

    /**
     * 0 正常，1 删除
     */
    private String fgValid;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    private Long extId;

}

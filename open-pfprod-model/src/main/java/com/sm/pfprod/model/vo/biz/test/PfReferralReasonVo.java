package com.sm.pfprod.model.vo.biz.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfReferralReasonVo implements Serializable {

    /**
     * 主键
     */
    private Long idReferralReason;

    /**
     * 确诊理由阶段
     */
    private String sdEvaEffciency;

    /**
     * 明细id
     */
    private Long id;

    /**
     * 阶段对应问诊、检查、检验名称
     */
    private String idText;

    /**
     * 对应问诊、检查、检验ID
     */
    private Long extId;


}

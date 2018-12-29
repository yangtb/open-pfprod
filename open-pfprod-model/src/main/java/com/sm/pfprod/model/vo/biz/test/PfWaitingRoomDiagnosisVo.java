package com.sm.pfprod.model.vo.biz.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfWaitingRoomDiagnosisVo implements Serializable {

    /**
     * 诊断id
     */
    private Long idTestexecResultDiagnosis;

    /**
     * 病例结果ID
     */
    private Long idTestexecResult;

    /**
     * 疾病ID
     */
    private Long idDie;

    /**
     * 疾病text
     */
    private String idDieText;

    /**
     * 病史小结id
     */
    private Long idTestexecResultSumary;

    /**
     * 病史小结
     */
    private String dieSumary;

}

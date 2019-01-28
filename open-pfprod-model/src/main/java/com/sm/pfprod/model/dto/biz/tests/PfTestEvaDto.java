package com.sm.pfprod.model.dto.biz.tests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfTestEvaDto implements Serializable {

    private static final long serialVersionUID = -1707262746906227797L;


    /**
     * 病例结果ID
     */
    private Long idTestexecResult;

    /**
     * 病例评估维度ID
     */
    private Long idTestexecResultDimension;

    /**
     * 病例id
     */
    private Long idMedicalrec;

    /**
     * 疾病字符串
     */
    private String idDieStr;

    /**
     * 类型 ：1=确诊项 2=排除拟诊项
     */
    private Integer type;

}

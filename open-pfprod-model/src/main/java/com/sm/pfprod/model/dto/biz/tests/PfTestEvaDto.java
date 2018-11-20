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
     * 病历评估维度ID
     */
    private Long idTestexecResultDimension;

}

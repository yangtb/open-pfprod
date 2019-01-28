package com.sm.pfprod.model.vo.biz.test.eva;

import com.sm.pfprod.model.entity.ExmEvaDimension;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfEvaExecVo extends ExmEvaDimension implements Serializable {

    /**
     * 评估项
     */
    private String pgItem;

    /**
     * 拟诊名称
     */
    private String nzName;

    /**
     * 病例结果ID
     */
    private Long parDimemsion;

    private String fgSystemAlgorithm;
}

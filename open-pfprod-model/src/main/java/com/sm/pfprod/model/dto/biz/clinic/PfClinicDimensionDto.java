package com.sm.pfprod.model.dto.biz.clinic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfClinicDimensionDto implements Serializable {

    private static final long serialVersionUID = 68120601303081385L;

    /**
     * 主键
     * 评估维度id
     */
    private Long idDimemsion;

    /**
     * 模板id
     */
    private Long idDemo;

}

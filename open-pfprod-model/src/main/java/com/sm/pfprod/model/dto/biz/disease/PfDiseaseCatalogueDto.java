package com.sm.pfprod.model.dto.biz.disease;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfDiseaseCatalogueDto implements Serializable {

    private static final long serialVersionUID = -7008573359759429447L;

    /**
     * 主键
     * 疾病目录ID
     */
    private Long idDieclass;
}

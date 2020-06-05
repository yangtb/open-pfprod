package com.sm.pfprod.model.dto.biz.drug;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfDrugCatalogueDto implements Serializable {

    private static final long serialVersionUID = -7008573359759429447L;

    /**
     * 主键
     * 药品目录ID
     */
    private Long idDrugsclass;
}

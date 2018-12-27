package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class FaqEvaCaseItemDiagnosis implements Serializable {

    private static final long serialVersionUID = 1540812319916L;


    /**
     * 主键
     * 明细id
     */
    private Long idEvaCaseItemList;

    /**
     * 病历评估项id
     */
    private Long idEvaCaseItem;

    /**
     * 疾病id
     */
    private Long idDie;

    /**
     * 疾病名称
     */
    private String idDieText;

}

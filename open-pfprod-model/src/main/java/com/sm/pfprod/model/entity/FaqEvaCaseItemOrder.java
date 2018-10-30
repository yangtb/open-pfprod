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
public class FaqEvaCaseItemOrder implements Serializable {

    private static final long serialVersionUID = 1540812310682L;


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
     * 1 临时用药 2 护理常规 3 护理级别 4 饮食 5 体位 6 长期用药
     */
    private String sdEvaOrder;

    /**
     * 药品id
     */
    private Long idShortDrugs;

    /**
     * 护理常规
     */
    private String sdNursRout;

    /**
     * 护理级别
     */
    private String cdNursLevel;

    /**
     * 饮食
     * isNullAble:0
     */
    private String sdDiet;

    /**
     * 体位
     */
    private String sdPosition;

    /**
     * 长期用药
     */
    private Long idLongDrugs;

    /**
     * 字典txt
     */
    private String desText;
}

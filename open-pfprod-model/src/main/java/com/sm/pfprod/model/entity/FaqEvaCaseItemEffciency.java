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
public class FaqEvaCaseItemEffciency implements Serializable {

    private static final long serialVersionUID = 1540812317022L;


    /**
     * 主键
     * 明细id
     */
    private Long idEvaCaseItemList;

    /**
     * 1. 问诊 2. 检查 3. 检验
     */
    private String sdEvaEffciency;

    /**
     * 病例评估项id
     */
    private Long idEvaCaseItem;

    /**
     * 数量上限
     */
    private Integer quaUpper;


}

package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 临床模拟_测试计划_关联病例
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmTestplanMedicalrec implements Serializable {

    private static final long serialVersionUID = 1541156590151L;

    /**
     * 主键
     * 关联病例ID
     */
    private Long idTestplanMedicalrec;

    /**
     * 测试计划ID
     */
    private Long idTestplan;

    /**
     * 病例ID
     */
    private Long idMedicalrec;

    /**
     * 排序
     */
    private Integer sort;


}

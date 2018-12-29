package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 临床模拟_测试执行_病例结果_问诊
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmMedResultInques implements Serializable {

    private static final long serialVersionUID = 1541867770936L;


    /**
     * 主键
     * <p>
     */
    private Long idTestexecResultInques;

    /**
     * 病例结果ID
     */
    private Long idTestexecResult;

    /**
     * 问题ID
     */
    private Long idInques;

    /**
     * 问诊明细ID
     */
    private Long idMedCaseList;

    /**
     * 线索标记
     */
    private String fgClue;

    /**
     * 0 正常，1 删除
     */
    private String fgValid;

    /**
     * 创建时间
     */
    private Date gmtCreate;

}

package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 临床模拟_测试执行_病历结果_诊断
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmMedResultDiagnosis implements Serializable {

    private static final long serialVersionUID = 1542177154552L;


    /**
     * 主键
     */
    private Long idTestexecResultDiagnosis;

    /**
     * 病历结果ID
     */
    private Long idTestexecResult;

    /**
     * 疾病ID
     */
    private Long idDie;

    /**
     * 0 正常，1 删除
     */
    private String fgValid;

    /**
     * 创建时间
     */
    private Date gmtCreate;

}

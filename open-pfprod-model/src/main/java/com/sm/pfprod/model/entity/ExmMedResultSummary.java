package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 临床模拟_测试执行_病例结果_病史小结
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmMedResultSummary implements Serializable {

    private static final long serialVersionUID = 1542177162675L;


    /**
     * 主键
     */
    private Long idTestexecResultSumary;

    /**
     * 病例结果ID
     */
    private Long idTestexecResult;

    /**
     * 病史小结
     */
    private String dieSumary;

    /**
     * 0 正常，1 删除
     */
    private String fgValid;

    /**
     * 创建时间
     */
    private Date gmtCreate;

}

package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 临床模拟_测试执行_病历结果_检查
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmMedResultBody implements Serializable {

    private static final long serialVersionUID = 1541987503459L;


    /**
     * 主键
     * <p>
     */
    private Long idTestexecResultBody;

    /**
     * 病历结果ID
     */
    private Long idTestexecResult;

    /**
     * 部位ID
     */
    private Long idBody;

    /**
     * 检查明细ID
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

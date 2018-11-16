package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.*;

/**
 * 临床模拟_测试执行_病历结果_检验
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmMedResultInspect implements Serializable {

    private static final long serialVersionUID = 1541987507060L;


    /**
     * 主键
     */
    private Long idTestexecResultInspect;

    /**
     * 病历结果ID
     */
    private Long idTestexecResult;

    /**
     * 检验明细ID
     */
    private Long idMedCaseList;

    /**
     * 检验项目ID
     */
    private Long idInspectItem;

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

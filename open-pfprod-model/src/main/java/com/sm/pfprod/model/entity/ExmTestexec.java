package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.*;

/**
 * 临床模拟_测试执行_关联病历
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmTestexec implements Serializable {

    private static final long serialVersionUID = 1541752042743L;


    /**
     * 主键
     */
    private Long idTestexec;

    /**
     * 测试明细ID
     */
    private Long idTestplanDetail;

    /**
     * 学生ID
     */
    private Long idStudent;

    /**
     * 病历ID
     */
    private Long idMedicalrec;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 归属机构
     */
    private Long idOrg;

    /**
     * 0 未执行 1 正在执行 2 执行完成
     */
    private String sdTestexec;

    /**
     * 执行开始时间
     */
    private Date dtExecBegin;

    /**
     * 执行结束时间
     */
    private Date dtExecEnd;

}

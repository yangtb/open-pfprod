package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmMedResultReferral implements Serializable {

    private static final long serialVersionUID = 1542098403343L;

    /**
     * 主键
     */
    private Long idTestexecResultReferral;

    /**
     * 病历结果ID
     */
    private Long idTestexecResult;

    /**
     * 1. 问诊
     * 2. 检查
     * 3. 检验
     */
    private String sdEvaReferral;

    /**
     * 拟诊ID
     */
    private Long idDie;

    /**
     * 疾病名称
     */
    private String idDieText;


    /**
     * 加入理由
     */
    private String reasonIn;

    /**
     * 排除标志
     */
    private String fgExclude;

    /**
     * 排除理由
     */
    private String reasonOut;

    /**
     * 0 正常，1 删除
     */
    private String fgValid;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 排除时间
     */
    private Date gmtModify;

}

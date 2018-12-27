package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.*;

/**
 * 临床模拟_测试执行_病历结果_医嘱
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmMedResultOrder implements Serializable {

    private static final long serialVersionUID = 1542159812961L;


    /**
     * 主键
     */
    private Long idTestexecResultOrder;

    /**
     * 病历结果ID
     */
    private Long idTestexecResult;

    /**
     * 一次性的检查或处置
     */
    private String desCheck;

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
     */
    private String sdDiet;

    /**
     * 体位
     */
    private String sdPosition;

    /**
     * 特殊处理
     */
    private String desSpecial;

    /**
     * 0 正常，1 删除
     */
    private String fgValid;

    /**
     * 创建时间
     */
    private Date gmtCreate;


}

package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 临床模拟_测试执行_病历结果_鉴别诊断
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmMedResultIdentify implements Serializable {

    private static final long serialVersionUID = 1570773221486L;


    /**
     * 主键
     * <p>
     * isNullAble:0
     */
    private Long idTestexecResultIdentify;

    /**
     * 病历结果ID
     * isNullAble:0
     */
    private Long idTestexecResult;

    /**
     * 鉴别疾病
     * isNullAble:0
     */
    private String naDie;

    /**
     * 鉴别理由
     * isNullAble:1
     */
    private String desDieReason;

    /**
     * 0 正常，1 删除
     * isNullAble:0,defaultVal:0
     */
    private String fgValid;

    /**
     * 创建时间
     * isNullAble:0
     */
    private Date gmtCreate;

    /**
     * 诊断_确诊理由
     */
    private String reasonList;


}

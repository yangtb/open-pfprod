package com.sm.pfprod.model.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 临床模拟_测试执行_病历结果_诊断_确诊理由
 *
 * @author yangtongbin
 */
@Setter
@Getter
@ToString
public class ExmMedResultIdentifyReason implements Serializable {

    private static final long serialVersionUID = 1570773224818L;


    /**
     * 主键
     * <p>
     * isNullAble:0
     */
    private Long idIdentifyReason;

    /**
     * 鉴别诊断ID
     * isNullAble:0
     */
    private Long idTestexecResultIdentify;

    /**
     * 确诊理由阶段
     * isNullAble:0
     */
    private String sdEvaEffciency;

    /**
     * 问诊
     * isNullAble:1
     */
    private Long idInques;

    /**
     * 检查
     * isNullAble:1
     */
    private Long idBody;

    /**
     * 检验
     * isNullAble:1
     */
    private Long idInspectItem;

    /**
     * 阶段对应问诊、检查、检验名称
     */
    private String idText;

    /**
     * 明细ID
     * isNullAble:0
     */
    private Long idMedCaseList;

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
     * 扩展ID
     */
    private Long extId;
}

package com.sm.pfprod.model.dto.biz.tests;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class PfTestExamTagDto extends PageParam implements Serializable {

    private static final long serialVersionUID = -1707262746906227797L;

    /**
     * 病例id
     */
    private Long idMedicalrec;

    /**
     * 病例组件编码
     */
    private String cdMedAsse;

    /**
     * 病例结果ID
     */
    private Long idTestexecResult;

    /**
     * 执行状态
     */
    private String sdTestexec;

    /**
     * 检查部位
     */
    private String sdBody;

    /**
     * 扩展字段 - 分类id
     */
    private Long extItemId;

    /**
     * 搜索关键字
     */
    private String keyword;

    private Long idMedCase;

    /**
     * 问诊标签
     */
    private String sdInquesLabel;

    /**
     * 前置条件的问诊项 1是 0否
     */
    private int inquesPreFlag;

    /**
     * 问诊ID
     */
    private Long idInques;

    /**
     * 检验ID
     */
    private Long idInspect;

    private boolean checked;

    /**
     * 类型
     */
    private Integer type;

    /**
     * idMedCaseList集合
     */
    private List<Long> idMedCaseLists;

    private String idTestexecResultReferral;

}

package com.sm.pfprod.model.dto.biz.kb.assess;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class PfAssessCommonSaveDto implements Serializable {

    private static final long serialVersionUID = 5699801286051115280L;

    /**
     * 模块：must
     */
    private String module;

    private List<String> names;

    private String sdEva;

    /**
     * 主键
     * 评估标签id
     */
    private Long idEvaTag;

    /**
     * 病例id
     */
    private Long idMedicalrec;

    /**
     * 所属病例名称
     */
    private String caseName;

    /**
     * 模板标签id
     */
    private Long idTag;

    /**
     * 评估组件案例id
     */
    private Long idEvaCase;

    /**
     * 为1时保存病例与标签关联管理
     */
    private String tagFlag;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 机构id
     */
    private Long idOrg;

    /**
     * 组件编码
     */
    private String cdEvaAsse;

    /**
     * 从病例引入标识
     */
    private int fromCaseFlag;

}

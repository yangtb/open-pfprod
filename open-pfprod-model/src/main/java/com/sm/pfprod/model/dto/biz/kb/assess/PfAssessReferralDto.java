package com.sm.pfprod.model.dto.biz.kb.assess;

import com.sm.pfprod.model.entity.FaqEvaCaseItem;
import com.sm.pfprod.model.entity.FaqEvaCaseItemReferral;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class PfAssessReferralDto extends FaqEvaCaseItem implements Serializable {


    private static final long serialVersionUID = 5866052701892123619L;

    /**
     * 评估阶段
     */
    private String sdEva;
    /**
     * 等效答案
     */
    private List<FaqEvaCaseItemReferral> list;


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
     * 原病例组件案例id
     */
    private Long oldIdEvaCase;

    /**
     * 机构id
     */
    private Long idOrg;

    /**
     * 组件编码
     */
    private String cdEvaAsse;

}

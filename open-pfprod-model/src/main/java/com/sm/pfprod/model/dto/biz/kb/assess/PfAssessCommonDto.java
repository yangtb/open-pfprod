package com.sm.pfprod.model.dto.biz.kb.assess;

import com.sm.pfprod.model.param.PageParam;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class PfAssessCommonDto extends PageParam implements Serializable {

    /**
     * 评估组件案例id
     */
    private Long idEvaCase;

    /**
     * 组件id
     */
    private String cdEvaAsse;

    /**
     * 病例评估项id
     */
    private Long idEvaCaseItem;

    /**
     * 评估阶段
     */
    private String sdType;

    /**
     * 扩展id
     */
    private Long extId;

}

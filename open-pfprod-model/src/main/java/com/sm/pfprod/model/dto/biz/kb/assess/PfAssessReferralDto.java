package com.sm.pfprod.model.dto.biz.kb.assess;

import com.sm.pfprod.model.entity.FaqEvaCaseItem;
import com.sm.pfprod.model.entity.FaqEvaCaseItemReferral;
import com.sm.pfprod.model.param.PageParam;
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

}

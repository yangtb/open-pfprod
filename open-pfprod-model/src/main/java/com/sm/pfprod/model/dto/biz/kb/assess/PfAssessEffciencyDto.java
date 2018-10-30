package com.sm.pfprod.model.dto.biz.kb.assess;

import com.sm.pfprod.model.entity.FaqEvaCaseItem;
import com.sm.pfprod.model.entity.FaqEvaCaseItemCover;
import com.sm.pfprod.model.entity.FaqEvaCaseItemEffciency;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class PfAssessEffciencyDto extends FaqEvaCaseItem implements Serializable {


    private static final long serialVersionUID = 5866052701892123619L;

    /**
     * 明细id
     */
    private Long idEvaCaseItemList;

    /**
     * 1. 问诊 2. 检查 3. 检验
     */
    private String sdEvaEffciency;


    /**
     * 数量上限
     */
    private Integer quaUpper;

}

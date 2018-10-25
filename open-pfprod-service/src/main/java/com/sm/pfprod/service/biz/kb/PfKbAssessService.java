package com.sm.pfprod.service.biz.kb;

import com.sm.pfprod.model.dto.biz.kb.assess.PfEvaCaseDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.FaqEvaCase;
import com.sm.pfprod.model.result.PageResult;

/**
 * @ClassName: PfKbAssessService
 * @Description: 评估组件用例
 * @Author yangtongbin
 * @Date 2018/10/22
 */
public interface PfKbAssessService {

    /**
     * 评估组件用例列表
     *
     * @param dto
     * @return
     */
    PageResult listKbAssess(PfEvaCaseDto dto);

    /**
     * 新增评估组件用例
     *
     * @param dto
     * @return
     */
    Long saveKbAssess(FaqEvaCase dto);

    /**
     * 删除评估组件用例
     *
     * @param dto
     * @return
     */
    boolean delKbAssess(PfBachChangeStatusDto dto);

}

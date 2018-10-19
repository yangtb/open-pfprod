package com.sm.pfprod.service.biz.kb;

import com.sm.pfprod.model.dto.biz.kb.part.PfMedCaseDto;
import com.sm.pfprod.model.dto.biz.kb.part.PfPartCommonDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.FaqMedCase;
import com.sm.pfprod.model.result.PageResult;

/**
 * @ClassName: PfKbPartService
 * @Description: 病例组件用例
 * @Author yangtongbin
 * @Date 2018/10/17
 */
public interface PfKbPartService {

    /**
     * 病例组件用例列表
     *
     * @param dto
     * @return
     */
    PageResult listKbPart(PfMedCaseDto dto);

    /**
     * 新增病例组件用例
     *
     * @param dto
     * @return
     */
    Long addKbPart(FaqMedCase dto);

    /**
     * 编辑病例组件用例
     *
     * @param dto
     * @return
     */
    boolean editKbPart(FaqMedCase dto);

    /**
     * 删除病例组件用例
     *
     * @param dto
     * @return
     */
    boolean delKbPart(PfBachChangeStatusDto dto);

    /**
     * 问诊_问题明细
     *
     * @param dto
     * @return
     */
    PageResult listFaqMedCaseInques(PfPartCommonDto dto);
}

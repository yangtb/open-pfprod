package com.sm.pfprod.service.biz.kb.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.kb.part.FaqMedCaseParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.part.PfMedCaseParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.part.PfPartCommonParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCaseInquesListResult;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCaseResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.kb.KbPartClient;
import com.sm.pfprod.model.dto.biz.kb.part.PfMedCaseDto;
import com.sm.pfprod.model.dto.biz.kb.part.PfPartCommonDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.FaqMedCase;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.kb.PfKbPartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("pfKbPartService")
public class PfKbPartServiceImpl implements PfKbPartService {

    @Resource
    private KbPartClient kbPartClient;

    @Override
    public PageResult listKbPart(PfMedCaseDto dto) {
        PfPageResult<FaqMedCaseResult> result = kbPartClient.listKbPart(BeanUtil.convert(dto, PfMedCaseParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public Long addKbPart(FaqMedCase dto) {
        CommonResult<Long> result = kbPartClient.addKbPart(BeanUtil.convert(dto, FaqMedCaseParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editKbPart(FaqMedCase dto) {
        CommonResult<Boolean> result = kbPartClient.editKbPart(BeanUtil.convert(dto, FaqMedCaseParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delKbPart(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = kbPartClient.delKbPart(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listFaqMedCaseInques(PfPartCommonDto dto) {
        PfPageResult<FaqMedCaseInquesListResult> result = kbPartClient.listFaqMedCaseInques(BeanUtil.convert(dto, PfPartCommonParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

}

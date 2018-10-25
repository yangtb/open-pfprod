package com.sm.pfprod.service.biz.kb.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.kb.assess.FaqEvaCaseParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.assess.PfEvaCaseParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.kb.assess.FaqEvaCaseResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.kb.KbAssessClient;
import com.sm.pfprod.model.dto.biz.kb.assess.PfEvaCaseDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.FaqEvaCase;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.kb.PfKbAssessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("pfKbAssessService")
public class PfKbAssessServiceImpl implements PfKbAssessService {

    @Resource
    private KbAssessClient kbAssessClient;

    @Override
    public PageResult listKbAssess(PfEvaCaseDto dto) {
        PfPageResult<FaqEvaCaseResult> result = kbAssessClient.listKbAssess(BeanUtil.convert(dto, PfEvaCaseParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public Long saveKbAssess(FaqEvaCase dto) {
        CommonResult<Long> result = kbAssessClient.saveKbAssess(BeanUtil.convert(dto, FaqEvaCaseParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delKbAssess(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = kbAssessClient.delKbAssess(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }
}

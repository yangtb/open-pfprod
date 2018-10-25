package com.sm.pfprod.integration.biz.kb;

import com.sm.open.core.facade.model.param.pf.biz.kb.assess.FaqEvaCaseParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.assess.PfEvaCaseParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.kb.PfKbAssessFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class KbAssessClient {

    @Resource
    private PfKbAssessFacade pfKbAssessFacade;

    public PfPageResult listKbAssess(PfEvaCaseParam param) {
        return pfKbAssessFacade.listKbAssess(param);
    }

    public CommonResult<Long> saveKbAssess(FaqEvaCaseParam param) {
        return pfKbAssessFacade.saveKbAssess(param);
    }

    public CommonResult<Boolean> delKbAssess(PfBachChangeStatusParam param) {
        return pfKbAssessFacade.delKbAssess(param);
    }
}

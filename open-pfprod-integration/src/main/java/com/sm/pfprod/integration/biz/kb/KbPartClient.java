package com.sm.pfprod.integration.biz.kb;

import com.sm.open.core.facade.model.param.pf.biz.kb.part.FaqMedCaseParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.part.PfMedCaseParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.part.PfPartCommonParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.kb.PfKbPartFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class KbPartClient {

    @Resource
    private PfKbPartFacade pfKbPartFacade;


    public PfPageResult listKbPart(PfMedCaseParam param) {
        return pfKbPartFacade.listKbPart(param);
    }

    public CommonResult<Long> addKbPart(FaqMedCaseParam param) {
        return pfKbPartFacade.addKbPart(param);
    }


    public CommonResult<Boolean> editKbPart(FaqMedCaseParam param) {
        return pfKbPartFacade.editKbPart(param);
    }

    public CommonResult<Boolean> delKbPart(PfBachChangeStatusParam param) {
        return pfKbPartFacade.delKbPart(param);
    }

    public PfPageResult listFaqMedCaseInques(PfPartCommonParam param) {
        return pfKbPartFacade.listFaqMedCaseInques(param);
    }
}

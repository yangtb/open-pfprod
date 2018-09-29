package com.sm.pfprod.integration.biz.disease;

import com.sm.open.core.facade.model.param.pf.biz.disease.BasDieParam;
import com.sm.open.core.facade.model.param.pf.biz.disease.PfDiseaseInfoParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.disease.PfDiseaseFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DiseaseClient {

    @Resource
    private PfDiseaseFacade pfDiseaseFacade;


    public PfPageResult listDiseaseInfo(PfDiseaseInfoParam param) {
        return pfDiseaseFacade.listDiseaseInfo(param);
    }

    public CommonResult<Boolean> addDiseaseInfo(BasDieParam param) {
        return pfDiseaseFacade.addDiseaseInfo(param);
    }

    public CommonResult<Boolean> editDiseaseInfo(BasDieParam param) {
        return pfDiseaseFacade.editDiseaseInfo(param);
    }

    public CommonResult<Boolean> delDiseaseInfo(PfBachChangeStatusParam param) {
        return pfDiseaseFacade.delDiseaseInfo(param);
    }
}

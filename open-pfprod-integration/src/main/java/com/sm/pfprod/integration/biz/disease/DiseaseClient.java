package com.sm.pfprod.integration.biz.disease;

import com.sm.open.core.facade.model.param.pf.biz.disease.PfDiseaseInfoParam;
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


}

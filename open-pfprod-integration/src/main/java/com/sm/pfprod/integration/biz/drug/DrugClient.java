package com.sm.pfprod.integration.biz.drug;

import com.sm.open.core.facade.model.param.pf.biz.drug.PfDrugInfoParam;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.drug.PfDrugFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DrugClient {

    @Resource
    private PfDrugFacade pfDrugFacade;


    public PfPageResult listDrugInfo(PfDrugInfoParam param) {
        return pfDrugFacade.listDrugInfo(param);
    }


}

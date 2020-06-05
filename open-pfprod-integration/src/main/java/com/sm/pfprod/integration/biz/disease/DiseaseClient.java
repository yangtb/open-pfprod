package com.sm.pfprod.integration.biz.disease;

import com.sm.open.core.facade.model.param.pf.biz.disease.BasDieClassParam;
import com.sm.open.core.facade.model.param.pf.biz.disease.BasDieParam;
import com.sm.open.core.facade.model.param.pf.biz.disease.PfDiseaseInfoParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.common.PfCatalogueTreeParam;
import com.sm.open.core.facade.model.param.pf.common.PfChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.disease.BasDieClassResult;
import com.sm.open.core.facade.model.result.pf.biz.disease.PfDiseaseZtreeResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.disease.PfDiseaseFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DiseaseClient {

    @Resource
    private PfDiseaseFacade pfDiseaseFacade;


    public CommonResult<List<PfDiseaseZtreeResult>> listDiseaseCatalogueTree(PfCatalogueTreeParam param) {
        return pfDiseaseFacade.listDiseaseCatalogueTree(param);
    }

    public CommonResult<BasDieClassResult> selectDiseaseDetail(Long idDieClass) {
        return pfDiseaseFacade.selectDiseaseDetail(idDieClass);
    }

    public CommonResult<Long> saveDiseaseCatalogue(BasDieClassParam param) {
        return pfDiseaseFacade.saveDiseaseCatalogue(param);
    }

    public CommonResult<Boolean> delDiseaseCatalogue(PfChangeStatusParam param) {
        return pfDiseaseFacade.delDiseaseCatalogue(param);
    }

    public PfPageResult listDiseaseInfo(PfDiseaseInfoParam param) {
        return pfDiseaseFacade.listDiseaseInfo(param);
    }

    public PfPageResult listIdeReason(PfDiseaseInfoParam param) {
        return pfDiseaseFacade.listIdeReason(param);
    }


    public PfPageResult listDiseaseByCatalogueId(PfDiseaseInfoParam param) {
        return pfDiseaseFacade.listDiseaseByCatalogueId(param);
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

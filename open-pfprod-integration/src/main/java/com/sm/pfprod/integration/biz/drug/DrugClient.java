package com.sm.pfprod.integration.biz.drug;

import com.sm.open.core.facade.model.param.pf.biz.drug.BasDrugsClassParam;
import com.sm.open.core.facade.model.param.pf.biz.drug.BasDrugsParam;
import com.sm.open.core.facade.model.param.pf.biz.drug.PfDrugInfoParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.common.PfCatalogueTreeParam;
import com.sm.open.core.facade.model.param.pf.common.PfChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.drug.BasDrugsClassResult;
import com.sm.open.core.facade.model.result.pf.biz.drug.PfDrugZtreeResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.drug.PfDrugFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class DrugClient {

    @Resource
    private PfDrugFacade pfDrugFacade;

    public CommonResult<List<PfDrugZtreeResult>> listDrugCatalogueTree(PfCatalogueTreeParam param) {
        return pfDrugFacade.listDrugCatalogueTree(param);
    }

    public CommonResult<BasDrugsClassResult> selectDrugDetail(Long idDrugsclass) {
        return pfDrugFacade.selectDrugDetail(idDrugsclass);
    }

    public CommonResult<Long> saveDrugCatalogue(BasDrugsClassParam param) {
        return pfDrugFacade.saveDrugCatalogue(param);
    }

    public CommonResult<Boolean> delDrugCatalogue(PfChangeStatusParam param) {
        return pfDrugFacade.delDrugCatalogue(param);
    }

    public PfPageResult listDrugInfo(PfDrugInfoParam param) {
        return pfDrugFacade.listDrugInfo(param);
    }


    public CommonResult<Boolean> addDrugInfo(BasDrugsParam param) {
        return pfDrugFacade.addDrugInfo(param);
    }


    public CommonResult<Boolean> editDrugInfo(BasDrugsParam param) {
        return pfDrugFacade.editDrugInfo(param);
    }


    public CommonResult<Boolean> delDrugInfo(PfBachChangeStatusParam param) {
        return pfDrugFacade.delDrugInfo(param);
    }
}

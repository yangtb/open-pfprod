package com.sm.pfprod.integration.biz.casehistory;

import com.sm.open.core.facade.model.param.pf.biz.casehistory.FaqMedicalrecCaParam;
import com.sm.open.core.facade.model.param.pf.biz.casehistory.FaqMedicalrecParam;
import com.sm.open.core.facade.model.param.pf.biz.casehistory.PfCaseHistoryParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.casehistory.PfCaseHistoryFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CaseHistoryClient {

    @Resource
    private PfCaseHistoryFacade pfCaseHistoryFacade;


    public CommonResult<List<PfCommonZtreeResult>> listClassifyTree() {
        return pfCaseHistoryFacade.listClassifyTree();
    }

    public CommonResult<Long> addClassify(FaqMedicalrecCaParam param) {
        return pfCaseHistoryFacade.addClassify(param);
    }

    public CommonResult<Boolean> editClassify(FaqMedicalrecCaParam param) {
        return pfCaseHistoryFacade.editClassify(param);
    }

    public CommonResult<Boolean> delClassify(PfBachChangeStatusParam param) {
        return pfCaseHistoryFacade.delClassify(param);
    }

    public PfPageResult listTemplate(PfCaseHistoryParam param) {
        return pfCaseHistoryFacade.listTemplate(param);
    }

    public CommonResult<Boolean> addTemplate(FaqMedicalrecParam param) {
        return pfCaseHistoryFacade.addTemplate(param);
    }

    public CommonResult<Boolean> editTemplate(FaqMedicalrecParam param) {
        return pfCaseHistoryFacade.editTemplate(param);
    }

    public CommonResult<Boolean> delTemplate(PfBachChangeStatusParam param) {
        return pfCaseHistoryFacade.delTemplate(param);
    }

}

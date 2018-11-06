package com.sm.pfprod.integration.biz.kb;

import com.sm.open.core.facade.model.param.pf.biz.clinic.FaqEvaTagParam;
import com.sm.open.core.facade.model.param.pf.biz.clinic.FaqMedTagParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.casehistory.FaqMedicalrecCaParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.casehistory.FaqMedicalrecParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.casehistory.PfCaseHistoryParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.clinic.PfAssessTagResult;
import com.sm.open.core.facade.model.result.pf.biz.clinic.PfCaseHistoryTagResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.kb.PfCaseHistoryFacade;
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

    public CommonResult<Long> saveMedTag(FaqMedTagParam param) {
        return pfCaseHistoryFacade.saveMedTag(param);
    }

    public CommonResult<Long> saveEvaTag(FaqEvaTagParam param) {
        return pfCaseHistoryFacade.saveEvaTag(param);
    }

    public CommonResult<List<PfCaseHistoryTagResult>> listAllCaseHistoryTag(Long idDemo) {
        return pfCaseHistoryFacade.listAllCaseHistoryTag(idDemo);
    }

    public CommonResult<List<PfAssessTagResult>> listAllAssessTag(Long idDemo) {
        return pfCaseHistoryFacade.listAllAssessTag(idDemo);
    }

}

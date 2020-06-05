package com.sm.pfprod.integration.biz.kb;

import com.sm.open.core.facade.model.param.pf.biz.clinic.FaqEvaTagParam;
import com.sm.open.core.facade.model.param.pf.biz.clinic.FaqMedTagParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.casehistory.FaqMedicalrecCaParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.casehistory.FaqMedicalrecParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.casehistory.PfCaseHistoryParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.clinic.FaqEvaTagResult;
import com.sm.open.core.facade.model.result.pf.biz.clinic.FaqMedTagResult;
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

    public CommonResult<List<PfCaseHistoryTagResult>> listAllCaseHistoryTag(Long idDemo, Long idMedicalrec) {
        return pfCaseHistoryFacade.listAllCaseHistoryTag(idDemo, idMedicalrec);
    }

    public CommonResult<List<PfAssessTagResult>> listAllAssessTag(Long idDemo, Long idMedicalrec) {
        return pfCaseHistoryFacade.listAllAssessTag(idDemo, idMedicalrec);
    }

    public CommonResult<FaqMedTagResult> selectMedTag(FaqMedTagParam param) {
        return pfCaseHistoryFacade.selectMedTag(param);
    }

    public CommonResult<FaqEvaTagResult> selectEvaTag(FaqEvaTagParam param) {
        return pfCaseHistoryFacade.selectEvaTag(param);
    }

    public CommonResult<Boolean> saveAsMed(FaqMedTagParam param) {
        return pfCaseHistoryFacade.saveAsMed(param);
    }

    public CommonResult<Boolean> saveAsEva(FaqEvaTagParam param) {
        return pfCaseHistoryFacade.saveAsEva(param);
    }

}

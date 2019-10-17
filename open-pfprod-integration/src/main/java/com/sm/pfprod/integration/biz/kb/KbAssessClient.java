package com.sm.pfprod.integration.biz.kb;

import com.sm.open.core.facade.model.param.pf.biz.kb.assess.*;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.kb.assess.*;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.kb.PfKbAssessFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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

    public PfPageResult listKbReferral(PfAssessCommonParam param) {
        return pfKbAssessFacade.listKbReferral(param);
    }

    public CommonResult<List<FaqEvaCaseItemReferralResult>> listReferral(PfAssessCommonParam param) {
        return pfKbAssessFacade.listReferral(param);
    }

    public CommonResult<Boolean> delReferral(PfBachChangeStatusParam param) {
        return pfKbAssessFacade.delReferral(param);
    }

    public CommonResult<Long> saveReferral(PfAssessReferralParam param) {
        return pfKbAssessFacade.saveReferral(param);
    }

    public PfPageResult listKbDiagnosis(PfAssessCommonParam param) {
        return pfKbAssessFacade.listKbDiagnosis(param);
    }

    public CommonResult<List<FaqEvaCaseItemDiagnosisResult>> listDiagnosisAnswer(PfAssessCommonParam param) {
        return pfKbAssessFacade.listDiagnosisAnswer(param);
    }

    public CommonResult<Boolean> delDiagnosis(PfBachChangeStatusParam param) {
        return pfKbAssessFacade.delDiagnosis(param);
    }

    public CommonResult<Long> saveDiagnosis(PfAssessDiagnosisParam param) {
        return pfKbAssessFacade.saveDiagnosis(param);
    }

    public PfPageResult listKbReason(PfAssessCommonParam param) {
        return pfKbAssessFacade.listKbReason(param);
    }

    public CommonResult<List<FaqEvaCaseItemReasonResult>> listReasonAnswer(PfAssessCommonParam param) {
        return pfKbAssessFacade.listReasonAnswer(param);
    }

    public CommonResult<Boolean> delReason(PfBachChangeStatusParam param) {
        return pfKbAssessFacade.delReason(param);
    }

    public CommonResult<Long> saveReason(PfAssessReasonParam param) {
        return pfKbAssessFacade.saveReason(param);
    }


    public PfPageResult listKbCover(PfAssessCommonParam param) {
        return pfKbAssessFacade.listKbCover(param);
    }

    public CommonResult<List<FaqEvaCaseItemCoverResult>> listCoverAnswer(PfAssessCommonParam param) {
        return pfKbAssessFacade.listCoverAnswer(param);
    }

    public CommonResult<Boolean> delCover(PfBachChangeStatusParam param) {
        return pfKbAssessFacade.delCover(param);
    }

    public CommonResult<Long> saveCover(PfAssessCoverParam param) {
        return pfKbAssessFacade.saveCover(param);
    }


    public PfPageResult listKbMust(PfAssessCommonParam param) {
        return pfKbAssessFacade.listKbMust(param);
    }

    public CommonResult<List<FaqEvaCaseItemMustResult>> listMustAnswer(PfAssessCommonParam param) {
        return pfKbAssessFacade.listMustAnswer(param);
    }

    public CommonResult<Boolean> delMust(PfBachChangeStatusParam param) {
        return pfKbAssessFacade.delMust(param);
    }

    public CommonResult<Long> saveMust(PfAssessMustParam param) {
        return pfKbAssessFacade.saveMust(param);
    }


    public PfPageResult listKbThorough(PfAssessCommonParam param) {
        return pfKbAssessFacade.listKbThorough(param);
    }

    public CommonResult<List<FaqEvaCaseItemThoroughResult>> listThoroughAnswer(PfAssessCommonParam param) {
        return pfKbAssessFacade.listThoroughAnswer(param);
    }

    public CommonResult<Boolean> delThorough(PfBachChangeStatusParam param) {
        return pfKbAssessFacade.delThorough(param);
    }

    public CommonResult<Long> saveThorough(PfAssessThoroughParam param) {
        return pfKbAssessFacade.saveThorough(param);
    }


    public PfPageResult listKbEffciency(PfAssessCommonParam param) {
        return pfKbAssessFacade.listKbEffciency(param);
    }

    public CommonResult<Boolean> delEffciency(PfBachChangeStatusParam param) {
        return pfKbAssessFacade.delEffciency(param);
    }

    public CommonResult<Long> saveEffciency(PfAssessEffciencyParam param) {
        return pfKbAssessFacade.saveEffciency(param);
    }

    public PfPageResult listKbOrder(PfAssessCommonParam param) {
        return pfKbAssessFacade.listKbOrder(param);
    }

    public CommonResult<List<FaqEvaCaseItemOrderResult>> listOrderAnswer(PfAssessCommonParam param) {
        return pfKbAssessFacade.listOrderAnswer(param);
    }

    public CommonResult<Boolean> delOrder(PfBachChangeStatusParam param) {
        return pfKbAssessFacade.delOrder(param);
    }

    public CommonResult<Long> saveOrder(PfAssessOrderParam param) {
        return pfKbAssessFacade.saveOrder(param);
    }

    public CommonResult<Boolean> delCommonAssess(PfBachChangeStatusParam param) {
        return pfKbAssessFacade.delCommonAssess(param);
    }

}

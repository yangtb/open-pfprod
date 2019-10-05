package com.sm.pfprod.integration.biz.kb;

import com.sm.open.core.facade.model.param.pf.biz.kb.PfSaveAsMedParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.part.*;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.*;
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

    public CommonResult<Long> saveFaqMedCaseInques(FaqMedCaseInquesListParam param) {
        return pfKbPartFacade.saveFaqMedCaseInques(param);
    }

    public CommonResult<Boolean> delFaqMedCaseInques(PfBachChangeStatusParam param) {
        return pfKbPartFacade.delFaqMedCaseInques(param);
    }

    public CommonResult<FaqMedCaseInquesListResult> resetKbCons(FaqMedCaseInquesListParam param) {
        return pfKbPartFacade.resetKbCons(param);
    }

    public CommonResult<Boolean> saveKbText(FaqMedCaseTextParam param) {
        return pfKbPartFacade.saveKbText(param);
    }

    public CommonResult<FaqMedCaseTextResult> selectKbText(Long idMedCase) {
        return pfKbPartFacade.selectKbText(idMedCase);
    }

    public CommonResult<Boolean> saveKbGuide(FaqMedCaseGuideParam param) {
        return pfKbPartFacade.saveKbGuide(param);
    }

    public CommonResult<FaqMedCaseGuideResult> selectKbGuide(Long idMedCase) {
        return pfKbPartFacade.selectKbGuide(idMedCase);
    }

    public CommonResult<Boolean> saveKbPic(FaqMedCasePicParam param) {
        return pfKbPartFacade.saveKbPic(param);
    }

    public CommonResult<FaqMedCasePicResult> selectKbPic(Long idMedCase) {
        return pfKbPartFacade.selectKbPic(idMedCase);
    }

    public CommonResult<Boolean> saveKbPat(FaqMedCasePatientParam param) {
        return pfKbPartFacade.saveKbPat(param);
    }

    public CommonResult<FaqMedCasePatientResult> selectKbPat(Long idMedCase) {
        return pfKbPartFacade.selectKbPat(idMedCase);
    }

    public PfPageResult<FaqMedCaseInspectListResult> listExams(PfPartCommonParam param) {
        return pfKbPartFacade.listExams(param);
    }

    public CommonResult<Long> saveExam(FaqMedCaseInspectListParam param) {
        return pfKbPartFacade.saveExam(param);
    }

    public CommonResult<Boolean> delKbExam(PfBachChangeStatusParam param) {
        return pfKbPartFacade.delKbExam(param);
    }

    public CommonResult<FaqMedCaseInspectListResult> resetKbExam(FaqMedCaseInspectListParam param) {
        return pfKbPartFacade.resetKbExam(param);
    }

    public PfPageResult listChecks(PfPartCommonParam param) {
        return pfKbPartFacade.listChecks(param);
    }

    public CommonResult<Long> saveCheck(FaqMedCaseBodyListparam param) {
        return pfKbPartFacade.saveCheck(param);
    }

    public CommonResult<Boolean> delKbCheck(PfBachChangeStatusParam param) {
        return pfKbPartFacade.delKbCheck(param);
    }

    public CommonResult<FaqMedCaseBodyListResult> resetKbCheck(FaqMedCaseBodyListparam param) {
        return pfKbPartFacade.resetKbCheck(param);
    }

    public CommonResult<Boolean> saveFaqMedCaseBody(FaqMedCaseBodyParam param) {
        return pfKbPartFacade.saveFaqMedCaseBody(param);
    }

    public CommonResult<FaqMedCaseBodyResult> selectFaqMedCaseBody(Long idMedCase) {
        return pfKbPartFacade.selectFaqMedCaseBody(idMedCase);
    }

    public CommonResult<Boolean> bachAddCons(PfSaveAsMedParam param) {
        return pfKbPartFacade.bachAddCons(param);
    }

    public CommonResult<Boolean> bachAddCheck(PfSaveAsMedParam param) {
        return pfKbPartFacade.bachAddCheck(param);
    }

    public CommonResult<Boolean> bachAddExam(PfSaveAsMedParam param) {
        return pfKbPartFacade.bachAddExam(param);
    }

    public PfPageResult<FaqMedCaseInquesListResult> listPreQuestion(FaqMedCaseInquesListParam param) {
        return pfKbPartFacade.listPreQuestion(param);
    }

}

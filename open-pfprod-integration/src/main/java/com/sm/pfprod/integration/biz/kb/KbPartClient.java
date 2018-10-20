package com.sm.pfprod.integration.biz.kb;

import com.sm.open.core.facade.model.param.pf.biz.kb.part.*;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCasePatientResult;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCasePicResult;
import com.sm.open.core.facade.model.result.pf.biz.kb.part.FaqMedCaseTextResult;
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

    public CommonResult<Long>  saveFaqMedCaseInques(FaqMedCaseInquesListParam param){
        return pfKbPartFacade.saveFaqMedCaseInques(param);
    }

    public CommonResult<Boolean> delFaqMedCaseInques(PfBachChangeStatusParam param){
        return pfKbPartFacade.delFaqMedCaseInques(param);
    }

    public CommonResult<Boolean> saveKbText(FaqMedCaseTextParam param) {
        return pfKbPartFacade.saveKbText(param);
    }

    public CommonResult<FaqMedCaseTextResult> selectKbText(Long idMedCase) {
        return pfKbPartFacade.selectKbText(idMedCase);
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
}

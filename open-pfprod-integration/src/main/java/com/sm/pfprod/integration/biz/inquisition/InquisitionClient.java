package com.sm.pfprod.integration.biz.inquisition;

import com.sm.open.core.facade.model.param.pf.biz.inquisition.BasInquesAnswerParam;
import com.sm.open.core.facade.model.param.pf.biz.inquisition.BasInquesCaParam;
import com.sm.open.core.facade.model.param.pf.biz.inquisition.BasInquesParam;
import com.sm.open.core.facade.model.param.pf.biz.inquisition.PfInquisitionQuestionParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.inquisition.BasInquesAnswerResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.inquisition.PfInquisitionFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class InquisitionClient {

    @Resource
    private PfInquisitionFacade pfInquisitionFacade;

    public CommonResult<List<PfCommonZtreeResult>> listQuestionClassifyTree() {
        return pfInquisitionFacade.listQuestionClassifyTree();
    }

    public CommonResult<Long> addQuestionClassify(BasInquesCaParam param) {
        return pfInquisitionFacade.addQuestionClassify(param);
    }

    public CommonResult<Boolean> editQuestionClassify(BasInquesCaParam param) {
        return pfInquisitionFacade.editQuestionClassify(param);
    }

    public CommonResult<Boolean> delQuestionClassify(PfBachChangeStatusParam param) {
        return pfInquisitionFacade.delQuestionClassify(param);
    }

    public PfPageResult listQuestion(PfInquisitionQuestionParam param) {
        return pfInquisitionFacade.listQuestion(param);
    }

    public CommonResult<Boolean> addQuestion(BasInquesParam param) {
        return pfInquisitionFacade.addQuestion(param);
    }

    public CommonResult<Boolean> editQuestion(BasInquesParam param) {
        return pfInquisitionFacade.editQuestion(param);
    }

    public CommonResult<Boolean> delQuestion(PfBachChangeStatusParam param) {
        return pfInquisitionFacade.delQuestion(param);
    }

    public PfPageResult<BasInquesAnswerResult> listAnswer(PfInquisitionQuestionParam param) {
        return pfInquisitionFacade.listAnswer(param);
    }

    public CommonResult<Boolean> delAnswer(PfBachChangeStatusParam param) {
        return pfInquisitionFacade.delAnswer(param);
    }

    public CommonResult<Long> saveAnswer(BasInquesAnswerParam param) {
        return pfInquisitionFacade.saveAnswer(param);
    }

}

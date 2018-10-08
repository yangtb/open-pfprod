package com.sm.pfprod.integration.biz.exam;

import com.sm.open.core.facade.model.param.pf.biz.exam.BasInspectCaParam;
import com.sm.open.core.facade.model.param.pf.biz.exam.BasInspectItemParam;
import com.sm.open.core.facade.model.param.pf.biz.exam.BasItemResultParam;
import com.sm.open.core.facade.model.param.pf.biz.exam.PfExamQuestionParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.exam.BasInspectItemResult;
import com.sm.open.core.facade.model.result.pf.biz.exam.BasItemResultResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.exam.PfExamFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ExamClient {

    @Resource
    private PfExamFacade pfExamFacade;

    public CommonResult<List<PfCommonZtreeResult>> listQuestionClassifyTree() {
        return pfExamFacade.listQuestionClassifyTree();
    }

    public CommonResult<Long> addQuestionClassify(BasInspectCaParam param) {
        return pfExamFacade.addQuestionClassify(param);
    }

    public CommonResult<Boolean> editQuestionClassify(BasInspectCaParam param) {
        return pfExamFacade.editQuestionClassify(param);
    }

    public CommonResult<Boolean> delQuestionClassify(PfBachChangeStatusParam param) {
        return pfExamFacade.delQuestionClassify(param);
    }

    public PfPageResult listQuestion(PfExamQuestionParam param) {
        return pfExamFacade.listQuestion(param);
    }

    public CommonResult<Boolean> addQuestion(BasInspectItemParam param) {
        return pfExamFacade.addQuestion(param);
    }

    public CommonResult<Boolean> editQuestion(BasInspectItemParam param) {
        return pfExamFacade.editQuestion(param);
    }

    public CommonResult<Boolean> delQuestion(PfBachChangeStatusParam param) {
        return pfExamFacade.delQuestion(param);
    }

    public PfPageResult<BasItemResultResult> listAnswer(PfExamQuestionParam param) {
        return pfExamFacade.listAnswer(param);
    }

    public CommonResult<Boolean> delAnswer(PfBachChangeStatusParam param) {
        return pfExamFacade.delAnswer(param);
    }

    public CommonResult<Long> saveAnswer(BasItemResultParam param) {
        return pfExamFacade.saveAnswer(param);
    }

}

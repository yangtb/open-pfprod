package com.sm.pfprod.integration.biz.check;

import com.sm.open.core.facade.model.param.pf.biz.check.BasBodyCaParam;
import com.sm.open.core.facade.model.param.pf.biz.check.BasBodyParam;
import com.sm.open.core.facade.model.param.pf.biz.check.BasBodyResultParam;
import com.sm.open.core.facade.model.param.pf.biz.check.PfCheckQuestionParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.check.BasBodyResultResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.biz.check.PfCheckFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class CheckClient {

    @Resource
    private PfCheckFacade pfCheckFacade;

    public CommonResult<List<PfCommonZtreeResult>> listQuestionClassifyTree() {
        return pfCheckFacade.listQuestionClassifyTree();
    }

    public CommonResult<Long> addQuestionClassify(BasBodyCaParam param) {
        return pfCheckFacade.addQuestionClassify(param);
    }

    public CommonResult<Boolean> editQuestionClassify(BasBodyCaParam param) {
        return pfCheckFacade.editQuestionClassify(param);
    }

    public CommonResult<Boolean> delQuestionClassify(PfBachChangeStatusParam param) {
        return pfCheckFacade.delQuestionClassify(param);
    }

    public PfPageResult listQuestion(PfCheckQuestionParam param) {
        return pfCheckFacade.listQuestion(param);
    }

    public CommonResult<Boolean> addQuestion(BasBodyParam param) {
        return pfCheckFacade.addQuestion(param);
    }

    public CommonResult<Boolean> editQuestion(BasBodyParam param) {
        return pfCheckFacade.editQuestion(param);
    }

    public CommonResult<Boolean> delQuestion(PfBachChangeStatusParam param) {
        return pfCheckFacade.delQuestion(param);
    }

    public PfPageResult<BasBodyResultResult> listAnswer(PfCheckQuestionParam param) {
        return pfCheckFacade.listAnswer(param);
    }

    public CommonResult<Boolean> delAnswer(PfBachChangeStatusParam param) {
        return pfCheckFacade.delAnswer(param);
    }

    public CommonResult<Long> saveAnswer(BasBodyResultParam param) {
        return pfCheckFacade.saveAnswer(param);
    }

}

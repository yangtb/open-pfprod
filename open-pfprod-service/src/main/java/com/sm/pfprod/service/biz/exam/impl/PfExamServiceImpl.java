package com.sm.pfprod.service.biz.exam.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.exam.BasInspectCaParam;
import com.sm.open.core.facade.model.param.pf.biz.exam.BasInspectItemParam;
import com.sm.open.core.facade.model.param.pf.biz.exam.BasItemResultParam;
import com.sm.open.core.facade.model.param.pf.biz.exam.PfExamQuestionParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.exam.BasInspectItemResult;
import com.sm.open.core.facade.model.result.pf.biz.exam.BasItemResultResult;
import com.sm.open.core.facade.model.result.pf.biz.inquisition.BasInquesAnswerResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.exam.ExamClient;
import com.sm.pfprod.model.dto.biz.exam.PfExamQuestionDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.BasInquesCa;
import com.sm.pfprod.model.entity.BasInspectCa;
import com.sm.pfprod.model.entity.BasInspectItem;
import com.sm.pfprod.model.entity.BasItemResult;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;
import com.sm.pfprod.service.biz.exam.PfExamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfExamService")
public class PfExamServiceImpl implements PfExamService {

    @Resource
    private ExamClient examClient;

    @Override
    public List<PfCommonZtreeVo> listQuestionClassifyTree() {
        CommonResult<List<PfCommonZtreeResult>> result = examClient.listQuestionClassifyTree();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfCommonZtreeVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long addQuestionClassify(BasInspectCa dto) {
        CommonResult<Long> result = examClient.addQuestionClassify(BeanUtil.convert(dto, BasInspectCaParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editQuestionClassify(BasInspectCa dto) {
        CommonResult<Boolean> result = examClient.editQuestionClassify(BeanUtil.convert(dto, BasInspectCaParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delQuestionClassify(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = examClient.delQuestionClassify(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listQuestion(PfExamQuestionDto dto) {
        PfPageResult<BasInspectItemResult> result = examClient.listQuestion(BeanUtil.convert(dto, PfExamQuestionParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addQuestion(BasInspectItem dto) {
        CommonResult<Boolean> result = examClient.addQuestion(BeanUtil.convert(dto, BasInspectItemParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editQuestion(BasInspectItem dto) {
        CommonResult<Boolean> result = examClient.editQuestion(BeanUtil.convert(dto, BasInspectItemParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delQuestion(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = examClient.delQuestion(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listAnswer(PfExamQuestionDto dto) {
        PfPageResult<BasItemResultResult> result = examClient.listAnswer(BeanUtil.convert(dto, PfExamQuestionParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean delAnswer(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = examClient.delAnswer(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveAnswer(BasItemResult dto) {
        CommonResult<Long> result = examClient.saveAnswer(BeanUtil.convert(dto, BasItemResultParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }
}

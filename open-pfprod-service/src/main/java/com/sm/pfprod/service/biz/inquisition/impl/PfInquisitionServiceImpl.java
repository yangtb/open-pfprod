package com.sm.pfprod.service.biz.inquisition.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.inquisition.BasInquesAnswerParam;
import com.sm.open.core.facade.model.param.pf.biz.inquisition.BasInquesCaParam;
import com.sm.open.core.facade.model.param.pf.biz.inquisition.BasInquesParam;
import com.sm.open.core.facade.model.param.pf.biz.inquisition.PfInquisitionQuestionParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.common.PfCommonSearchParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.inquisition.BasInquesAnswerResult;
import com.sm.open.core.facade.model.result.pf.biz.inquisition.BasInquesResult;
import com.sm.open.core.facade.model.result.pf.biz.inquisition.BasInquesSearchResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.inquisition.InquisitionClient;
import com.sm.pfprod.model.dto.biz.inquisition.PfInquisitionQuestionDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCommonSearchDto;
import com.sm.pfprod.model.entity.BasInques;
import com.sm.pfprod.model.entity.BasInquesAnswer;
import com.sm.pfprod.model.entity.BasInquesCa;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;
import com.sm.pfprod.service.biz.inquisition.PfInquisitionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfInquisitionService")
public class PfInquisitionServiceImpl implements PfInquisitionService {

    @Resource
    private InquisitionClient inquisitionClient;

    @Override
    public List<PfCommonZtreeVo> listQuestionClassifyTree() {
        CommonResult<List<PfCommonZtreeResult>> result = inquisitionClient.listQuestionClassifyTree();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfCommonZtreeVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public String listQuestionClassifyTreeSelect() {
        CommonResult<String> result = inquisitionClient.listQuestionClassifyTreeSelect();
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long addQuestionClassify(BasInquesCa dto) {
        CommonResult<Long> result = inquisitionClient.addQuestionClassify(BeanUtil.convert(dto, BasInquesCaParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editQuestionClassify(BasInquesCa dto) {
        CommonResult<Boolean> result = inquisitionClient.editQuestionClassify(BeanUtil.convert(dto, BasInquesCaParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delQuestionClassify(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = inquisitionClient.delQuestionClassify(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listQuestion(PfInquisitionQuestionDto dto) {
        PfPageResult<BasInquesResult> result = inquisitionClient.listQuestion(BeanUtil.convert(dto, PfInquisitionQuestionParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public PageResult listPreQuestion(PfInquisitionQuestionDto dto) {
        PfPageResult<BasInquesResult> result = inquisitionClient.listPreQuestion(BeanUtil.convert(dto, PfInquisitionQuestionParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addQuestion(BasInques dto) {
        CommonResult<Boolean> result = inquisitionClient.addQuestion(BeanUtil.convert(dto, BasInquesParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editQuestion(BasInques dto) {
        CommonResult<Boolean> result = inquisitionClient.editQuestion(BeanUtil.convert(dto, BasInquesParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delQuestion(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = inquisitionClient.delQuestion(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listAnswer(PfInquisitionQuestionDto dto) {
        PfPageResult<BasInquesAnswerResult> result = inquisitionClient.listAnswer(BeanUtil.convert(dto, PfInquisitionQuestionParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean delAnswer(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = inquisitionClient.delAnswer(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveAnswer(BasInquesAnswer dto) {
        CommonResult<Long> result = inquisitionClient.saveAnswer(BeanUtil.convert(dto, BasInquesAnswerParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult searchQuestion(PfCommonSearchDto dto) {
        PfPageResult<BasInquesSearchResult> result = inquisitionClient.searchQuestion(BeanUtil.convert(dto, PfCommonSearchParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

}

package com.sm.pfprod.service.biz.check.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.check.BasBodyCaParam;
import com.sm.open.core.facade.model.param.pf.biz.check.BasBodyParam;
import com.sm.open.core.facade.model.param.pf.biz.check.BasBodyResultParam;
import com.sm.open.core.facade.model.param.pf.biz.check.PfCheckQuestionParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.common.PfCommonSearchParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.check.BasBodyCheckResult;
import com.sm.open.core.facade.model.result.pf.biz.check.BasBodyResultResult;
import com.sm.open.core.facade.model.result.pf.biz.check.BasCheckSearchResult;
import com.sm.open.core.facade.model.result.pf.biz.exam.BasExamSearchResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.check.CheckClient;
import com.sm.pfprod.model.dto.biz.check.PfCheckQuestionDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.common.PfCommonSearchDto;
import com.sm.pfprod.model.entity.BasBody;
import com.sm.pfprod.model.entity.BasBodyCa;
import com.sm.pfprod.model.entity.BasBodyResult;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;
import com.sm.pfprod.service.biz.check.PfCheckService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfCheckService")
public class PfCheckServiceImpl implements PfCheckService {

    @Resource
    private CheckClient checkClient;

    @Override
    public List<PfCommonZtreeVo> listQuestionClassifyTree() {
        CommonResult<List<PfCommonZtreeResult>> result = checkClient.listQuestionClassifyTree();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfCommonZtreeVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long addQuestionClassify(BasBodyCa dto) {
        CommonResult<Long> result = checkClient.addQuestionClassify(BeanUtil.convert(dto, BasBodyCaParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editQuestionClassify(BasBodyCa dto) {
        CommonResult<Boolean> result = checkClient.editQuestionClassify(BeanUtil.convert(dto, BasBodyCaParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delQuestionClassify(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = checkClient.delQuestionClassify(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listQuestion(PfCheckQuestionDto dto) {
        PfPageResult<BasBodyCheckResult> result = checkClient.listQuestion(BeanUtil.convert(dto, PfCheckQuestionParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addQuestion(BasBody dto) {
        CommonResult<Boolean> result = checkClient.addQuestion(BeanUtil.convert(dto, BasBodyParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editQuestion(BasBody dto) {
        CommonResult<Boolean> result = checkClient.editQuestion(BeanUtil.convert(dto, BasBodyParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delQuestion(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = checkClient.delQuestion(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listAnswer(PfCheckQuestionDto dto) {
        PfPageResult<BasBodyResultResult> result = checkClient.listAnswer(BeanUtil.convert(dto, PfCheckQuestionParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean delAnswer(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = checkClient.delAnswer(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveAnswer(BasBodyResult dto) {
        CommonResult<Long> result = checkClient.saveAnswer(BeanUtil.convert(dto, BasBodyResultParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult searchCheck(PfCommonSearchDto dto) {
        PfPageResult<BasCheckSearchResult> result = checkClient.searchCheck(BeanUtil.convert(dto, PfCommonSearchParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }
}

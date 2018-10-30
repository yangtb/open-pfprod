package com.sm.pfprod.service.biz.kb.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.kb.assess.*;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.kb.assess.*;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.kb.KbAssessClient;
import com.sm.pfprod.model.dto.biz.kb.assess.*;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.*;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.kb.PfKbAssessService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfKbAssessService")
public class PfKbAssessServiceImpl implements PfKbAssessService {

    @Resource
    private KbAssessClient kbAssessClient;

    @Override
    public PageResult listKbAssess(PfEvaCaseDto dto) {
        PfPageResult<FaqEvaCaseResult> result = kbAssessClient.listKbAssess(BeanUtil.convert(dto, PfEvaCaseParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public Long saveKbAssess(FaqEvaCase dto) {
        CommonResult<Long> result = kbAssessClient.saveKbAssess(BeanUtil.convert(dto, FaqEvaCaseParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delKbAssess(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = kbAssessClient.delKbAssess(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listKbReferral(PfAssessCommonDto dto) {
        PfPageResult<FaqEvaCaseItemResult> result = kbAssessClient.listKbReferral(BeanUtil.convert(dto, PfAssessCommonParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public List<FaqEvaCaseItemReferral> listReferral(PfAssessCommonDto dto) {
        CommonResult<List<FaqEvaCaseItemReferralResult>> result = kbAssessClient.listReferral(BeanUtil.convert(dto, PfAssessCommonParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), FaqEvaCaseItemReferral.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delReferral(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = kbAssessClient.delReferral(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveReferral(PfAssessReferralDto dto) {
        List<FaqEvaCaseItemReferralParam> list = BeanUtil.convertList(dto.getList(), FaqEvaCaseItemReferralParam.class);
        PfAssessReferralParam param = BeanUtil.convert(dto, PfAssessReferralParam.class);
        param.setList(list);
        CommonResult<Long> result = kbAssessClient.saveReferral(param);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listKbDiagnosis(PfAssessCommonDto dto) {
        PfPageResult<FaqEvaCaseItemResult> result = kbAssessClient.listKbDiagnosis(BeanUtil.convert(dto, PfAssessCommonParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public List<FaqEvaCaseItemDiagnosis> listDiagnosisAnswer(PfAssessCommonDto dto) {
        CommonResult<List<FaqEvaCaseItemDiagnosisResult>> result = kbAssessClient.listDiagnosisAnswer(BeanUtil.convert(dto, PfAssessCommonParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), FaqEvaCaseItemDiagnosis.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delDiagnosis(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = kbAssessClient.delDiagnosis(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveDiagnosis(PfAssessDiagnosisDto dto) {
        List<FaqEvaCaseItemDiagnosisParam> list = BeanUtil.convertList(dto.getList(), FaqEvaCaseItemDiagnosisParam.class);
        PfAssessDiagnosisParam param = BeanUtil.convert(dto, PfAssessDiagnosisParam.class);
        param.setList(list);
        CommonResult<Long> result = kbAssessClient.saveDiagnosis(param);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listKbReason(PfAssessCommonDto dto) {
        PfPageResult<FaqEvaCaseItemResult> result = kbAssessClient.listKbReason(BeanUtil.convert(dto, PfAssessCommonParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public List<FaqEvaCaseItemReason> listReasonAnswer(PfAssessCommonDto dto) {
        CommonResult<List<FaqEvaCaseItemReasonResult>> result = kbAssessClient.listReasonAnswer(BeanUtil.convert(dto, PfAssessCommonParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), FaqEvaCaseItemReason.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delReason(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = kbAssessClient.delReason(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveReason(PfAssessReasonDto dto) {
        List<FaqEvaCaseItemReasonParam> list = BeanUtil.convertList(dto.getList(), FaqEvaCaseItemReasonParam.class);
        PfAssessReasonParam param = BeanUtil.convert(dto, PfAssessReasonParam.class);
        param.setList(list);
        CommonResult<Long> result = kbAssessClient.saveReason(param);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listKbCover(PfAssessCommonDto dto) {
        PfPageResult<FaqEvaCaseItemResult> result = kbAssessClient.listKbCover(BeanUtil.convert(dto, PfAssessCommonParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public List<FaqEvaCaseItemCover> listCoverAnswer(PfAssessCommonDto dto) {
        CommonResult<List<FaqEvaCaseItemCoverResult>> result = kbAssessClient.listCoverAnswer(BeanUtil.convert(dto, PfAssessCommonParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), FaqEvaCaseItemCover.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delCover(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = kbAssessClient.delCover(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveCover(PfAssessCoverDto dto) {
        List<FaqEvaCaseItemCoverParam> list = BeanUtil.convertList(dto.getList(), FaqEvaCaseItemCoverParam.class);
        PfAssessCoverParam param = BeanUtil.convert(dto, PfAssessCoverParam.class);
        param.setList(list);
        CommonResult<Long> result = kbAssessClient.saveCover(param);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listKbMust(PfAssessCommonDto dto) {
        PfPageResult<FaqEvaCaseItemResult> result = kbAssessClient.listKbMust(BeanUtil.convert(dto, PfAssessCommonParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public List<FaqEvaCaseItemMust> listMustAnswer(PfAssessCommonDto dto) {
        CommonResult<List<FaqEvaCaseItemMustResult>> result = kbAssessClient.listMustAnswer(BeanUtil.convert(dto, PfAssessCommonParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), FaqEvaCaseItemMust.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delMust(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = kbAssessClient.delMust(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveMust(PfAssessMustDto dto) {
        List<FaqEvaCaseItemMustParam> list = BeanUtil.convertList(dto.getList(), FaqEvaCaseItemMustParam.class);
        PfAssessMustParam param = BeanUtil.convert(dto, PfAssessMustParam.class);
        param.setList(list);
        CommonResult<Long> result = kbAssessClient.saveMust(param);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listKbEffciency(PfAssessCommonDto dto) {
        PfPageResult<FaqEvaCaseItemResult> result = kbAssessClient.listKbEffciency(BeanUtil.convert(dto, PfAssessCommonParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean delEffciency(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = kbAssessClient.delEffciency(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveEffciency(PfAssessEffciencyDto dto) {
        PfAssessEffciencyParam param = BeanUtil.convert(dto, PfAssessEffciencyParam.class);
        CommonResult<Long> result = kbAssessClient.saveEffciency(param);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listKbOrder(PfAssessCommonDto dto) {
        PfPageResult<FaqEvaCaseItemResult> result = kbAssessClient.listKbOrder(BeanUtil.convert(dto, PfAssessCommonParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public List<FaqEvaCaseItemOrder> listOrderAnswer(PfAssessCommonDto dto) {
        CommonResult<List<FaqEvaCaseItemOrderResult>> result = kbAssessClient.listOrderAnswer(BeanUtil.convert(dto, PfAssessCommonParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), FaqEvaCaseItemOrder.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delOrder(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = kbAssessClient.delOrder(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveOrder(PfAssessOrderDto dto) {
        List<FaqEvaCaseItemOrderParam> list = BeanUtil.convertList(dto.getList(), FaqEvaCaseItemOrderParam.class);
        PfAssessOrderParam param = BeanUtil.convert(dto, PfAssessOrderParam.class);
        param.setList(list);
        CommonResult<Long> result = kbAssessClient.saveOrder(param);
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }
}

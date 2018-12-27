package com.sm.pfprod.service.biz.kb.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.clinic.FaqEvaTagParam;
import com.sm.open.core.facade.model.param.pf.biz.clinic.FaqMedTagParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.casehistory.FaqMedicalrecCaParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.casehistory.FaqMedicalrecParam;
import com.sm.open.core.facade.model.param.pf.biz.kb.casehistory.PfCaseHistoryParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.clinic.FaqEvaTagResult;
import com.sm.open.core.facade.model.result.pf.biz.clinic.FaqMedTagResult;
import com.sm.open.core.facade.model.result.pf.biz.clinic.PfAssessTagResult;
import com.sm.open.core.facade.model.result.pf.biz.clinic.PfCaseHistoryTagResult;
import com.sm.open.core.facade.model.result.pf.biz.kb.casehistory.FaqMedicalrecResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.kb.CaseHistoryClient;
import com.sm.pfprod.model.dto.biz.kb.casehistory.PfCaseHistoryDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.FaqEvaTag;
import com.sm.pfprod.model.entity.FaqMedTag;
import com.sm.pfprod.model.entity.FaqMedicalrec;
import com.sm.pfprod.model.entity.FaqMedicalrecCa;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;
import com.sm.pfprod.model.vo.biz.clinic.PfAssessTagVo;
import com.sm.pfprod.model.vo.biz.clinic.PfCaseHistoryTagVo;
import com.sm.pfprod.service.biz.kb.PfCaseHistoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfCaseHistoryService")
public class PfCaseHistoryServiceImpl implements PfCaseHistoryService {

    @Resource
    private CaseHistoryClient caseHistoryClient;

    @Override
    public List<PfCommonZtreeVo> listClassifyTree() {
        CommonResult<List<PfCommonZtreeResult>> result = caseHistoryClient.listClassifyTree();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfCommonZtreeVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long addClassify(FaqMedicalrecCa dto) {
        CommonResult<Long> result = caseHistoryClient.addClassify(BeanUtil.convert(dto, FaqMedicalrecCaParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editClassify(FaqMedicalrecCa dto) {
        CommonResult<Boolean> result = caseHistoryClient.editClassify(BeanUtil.convert(dto, FaqMedicalrecCaParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delClassify(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = caseHistoryClient.delClassify(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listTemplate(PfCaseHistoryDto dto) {
        PfPageResult<FaqMedicalrecResult> result = caseHistoryClient.listTemplate(BeanUtil.convert(dto, PfCaseHistoryParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addTemplate(FaqMedicalrec dto) {
        CommonResult<Boolean> result = caseHistoryClient.addTemplate(BeanUtil.convert(dto, FaqMedicalrecParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editTemplate(FaqMedicalrec dto) {
        CommonResult<Boolean> result = caseHistoryClient.editTemplate(BeanUtil.convert(dto, FaqMedicalrecParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delTemplate(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = caseHistoryClient.delTemplate(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveMedTag(FaqMedTag dto) {
        CommonResult<Long> result = caseHistoryClient.saveMedTag(BeanUtil.convert(dto, FaqMedTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveEvaTag(FaqEvaTag dto) {
        CommonResult<Long> result = caseHistoryClient.saveEvaTag(BeanUtil.convert(dto, FaqEvaTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfCaseHistoryTagVo> listAllCaseHistoryTag(Long idDemo, Long idMedicalrec) {
        CommonResult<List<PfCaseHistoryTagResult>> result = caseHistoryClient.listAllCaseHistoryTag(idDemo, idMedicalrec);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfCaseHistoryTagVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfAssessTagVo> listAllAssessTag(Long idDemo, Long idMedicalrec) {
        CommonResult<List<PfAssessTagResult>> result = caseHistoryClient.listAllAssessTag(idDemo, idMedicalrec);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfAssessTagVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public FaqMedTag selectMedTag(FaqMedTag dto) {
        CommonResult<FaqMedTagResult> result = caseHistoryClient.selectMedTag(BeanUtil.convert(dto, FaqMedTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), FaqMedTag.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public FaqEvaTag selectEvaTag(FaqEvaTag dto) {
        CommonResult<FaqEvaTagResult> result = caseHistoryClient.selectEvaTag(BeanUtil.convert(dto, FaqEvaTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), FaqEvaTag.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean saveAsMed(FaqMedTag dto) {
        CommonResult<Boolean> result = caseHistoryClient.saveAsMed(BeanUtil.convert(dto, FaqMedTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean saveAsEva(FaqEvaTag dto) {
        CommonResult<Boolean> result = caseHistoryClient.saveAsEva(BeanUtil.convert(dto, FaqEvaTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }
}

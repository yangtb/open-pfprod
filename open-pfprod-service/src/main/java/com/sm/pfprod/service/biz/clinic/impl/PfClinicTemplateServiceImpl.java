package com.sm.pfprod.service.biz.clinic.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.clinic.*;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.clinic.*;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.clinic.ClinicTemplateClient;
import com.sm.pfprod.model.dto.biz.clinic.PfClinicTemplateDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.*;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;
import com.sm.pfprod.model.vo.biz.clinic.PfAssessTagVo;
import com.sm.pfprod.model.vo.biz.clinic.PfCaseHistoryTagVo;
import com.sm.pfprod.service.biz.clinic.PfClinicTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfClinicTemplateService")
public class PfClinicTemplateServiceImpl implements PfClinicTemplateService {

    @Resource
    private ClinicTemplateClient clinicTemplateClient;

    @Override
    public List<PfCommonZtreeVo> listClassifyTree() {
        CommonResult<List<PfCommonZtreeResult>> result = clinicTemplateClient.listClassifyTree();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfCommonZtreeVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long addClassify(BasDemoCa dto) {
        CommonResult<Long> result = clinicTemplateClient.addClassify(BeanUtil.convert(dto, BasDemoCaParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editClassify(BasDemoCa dto) {
        CommonResult<Boolean> result = clinicTemplateClient.editClassify(BeanUtil.convert(dto, BasDemoCaParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delClassify(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = clinicTemplateClient.delClassify(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listTemplate(PfClinicTemplateDto dto) {
        PfPageResult<BasDemoResult> result = clinicTemplateClient.listTemplate(BeanUtil.convert(dto, PfClinicTemplateParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addTemplate(BasDemo dto) {
        CommonResult<Boolean> result = clinicTemplateClient.addTemplate(BeanUtil.convert(dto, BasDemoParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editTemplate(BasDemo dto) {
        CommonResult<Boolean> result = clinicTemplateClient.editTemplate(BeanUtil.convert(dto, BasDemoParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delTemplate(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = clinicTemplateClient.delTemplate(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listCaseHistoryTag(PfClinicTemplateDto dto) {
        PfPageResult<BasMedicalTagVoResult> result = clinicTemplateClient.listCaseHistoryTag(BeanUtil.convert(dto, PfClinicTemplateParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean delCaseHistoryTag(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = clinicTemplateClient.delCaseHistoryTag(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveCaseHistoryTag(BasMedicalTag dto) {
        CommonResult<Long> result = clinicTemplateClient.saveCaseHistoryTag(BeanUtil.convert(dto, BasMedicalTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listSheetTag(PfClinicTemplateDto dto) {
        PfPageResult<BasEvaTagVoResult> result = clinicTemplateClient.listSheetTag(BeanUtil.convert(dto, PfClinicTemplateParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean delSheetTag(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = clinicTemplateClient.delSheetTag(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveSheetTag(BasEvaTag dto) {
        CommonResult<Long> result = clinicTemplateClient.saveSheetTag(BeanUtil.convert(dto, BasEvaTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<BasDemo> listAllBasDemo() {
        CommonResult<List<BasDemoResult>> result = clinicTemplateClient.listAllBasDemo();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), BasDemo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<BasDemoTag> listTagByIdDemo(Long idDemo) {
        CommonResult<List<BasDemoTagResult>> result = clinicTemplateClient.listTagByIdDemo(idDemo);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), BasDemoTag.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfCommonZtreeVo> listDimensionTree(Long idDemo) {
        CommonResult<List<PfCommonZtreeResult>> result = clinicTemplateClient.listDimensionTree(idDemo);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfCommonZtreeVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delDimensionTag(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = clinicTemplateClient.delDimensionTag(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveDimensionTag(BasDemoAsses dto) {
        CommonResult<Long> result = clinicTemplateClient.saveDimensionTag(BeanUtil.convert(dto, BasDemoAssesParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public BasDemoAsses selectDimensionTagInfo(Long idDimemsion) {
        CommonResult<BasDemoAssesResult> result = clinicTemplateClient.selectDimensionTagInfo(idDimemsion);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), BasDemoAsses.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfCaseHistoryTagVo> listAllCaseHistoryTag(Long idDemo) {
        CommonResult<List<PfCaseHistoryTagResult>> result = clinicTemplateClient.listAllCaseHistoryTag(idDemo);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfCaseHistoryTagVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfAssessTagVo> listAllAssessTag(Long idDemo) {
        CommonResult<List<PfAssessTagResult>> result = clinicTemplateClient.listAllAssessTag(idDemo);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfAssessTagVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean saveSerialNo(BasMedicalTag dto) {
        CommonResult<Boolean> result = clinicTemplateClient.saveSerialNo(BeanUtil.convert(dto, BasMedicalTagParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

}

package com.sm.pfprod.service.biz.clinic.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.clinic.BasDemoCaParam;
import com.sm.open.core.facade.model.param.pf.biz.clinic.BasDemoParam;
import com.sm.open.core.facade.model.param.pf.biz.clinic.BasDemoTagParam;
import com.sm.open.core.facade.model.param.pf.biz.clinic.PfClinicTemplateParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.PfCommonZtreeResult;
import com.sm.open.core.facade.model.result.pf.biz.clinic.BasDemoResult;
import com.sm.open.core.facade.model.result.pf.biz.clinic.BasDemoTagResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.clinic.ClinicTemplateClient;
import com.sm.pfprod.model.dto.biz.clinic.PfClinicTemplateDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.BasDemo;
import com.sm.pfprod.model.entity.BasDemoCa;
import com.sm.pfprod.model.entity.BasDemoTag;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.biz.PfCommonZtreeVo;
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
    public PageResult listTag(PfClinicTemplateDto dto) {
        PfPageResult<BasDemoTagResult> result = clinicTemplateClient.listTag(BeanUtil.convert(dto, PfClinicTemplateParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean delTag(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = clinicTemplateClient.delTag(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Long saveTag(BasDemoTag dto) {
        CommonResult<Long> result = clinicTemplateClient.saveTag(BeanUtil.convert(dto, BasDemoTagParam.class));
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

}

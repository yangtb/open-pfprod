package com.sm.pfprod.service.biz.clinic.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.clinic.parts.BasAlgorithmParam;
import com.sm.open.core.facade.model.param.pf.biz.clinic.parts.BasEvaAsseParam;
import com.sm.open.core.facade.model.param.pf.biz.clinic.parts.BasMedAsseParam;
import com.sm.open.core.facade.model.param.pf.biz.clinic.parts.PfClinicPartsParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.clinic.parts.BasAlgorithmResult;
import com.sm.open.core.facade.model.result.pf.biz.clinic.parts.BasEvaAsseResult;
import com.sm.open.core.facade.model.result.pf.biz.clinic.parts.BasMedAsseResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.clinic.ClinicPartsClient;
import com.sm.pfprod.model.dto.biz.clinic.parts.PfClinicPartsDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.BasAlgorithm;
import com.sm.pfprod.model.entity.BasEvaAsse;
import com.sm.pfprod.model.entity.BasMedAsse;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.clinic.PfClinicPartsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfClinicPartsService")
public class PfClinicPartsServiceImpl implements PfClinicPartsService {

    @Resource
    private ClinicPartsClient clinicPartsClient;

    @Override
    public PageResult listParts(PfClinicPartsDto dto) {
        PfPageResult<BasMedAsseResult> result = clinicPartsClient.listParts(BeanUtil.convert(dto, PfClinicPartsParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addPart(BasMedAsse dto) {
        CommonResult<Boolean> result = clinicPartsClient.addPart(BeanUtil.convert(dto, BasMedAsseParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editPart(BasMedAsse dto) {
        CommonResult<Boolean> result = clinicPartsClient.editPart(BeanUtil.convert(dto, BasMedAsseParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delPart(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = clinicPartsClient.delPart(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listSheet(PfClinicPartsDto dto) {
        PfPageResult<BasEvaAsseResult> result = clinicPartsClient.listSheet(BeanUtil.convert(dto, PfClinicPartsParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addSheet(BasEvaAsse dto) {
        CommonResult<Boolean> result = clinicPartsClient.addSheet(BeanUtil.convert(dto, BasEvaAsseParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editSheet(BasEvaAsse dto) {
        CommonResult<Boolean> result = clinicPartsClient.editSheet(BeanUtil.convert(dto, BasEvaAsseParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delSheet(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = clinicPartsClient.delSheet(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public PageResult listAlgorithm(PfClinicPartsDto dto) {
        PfPageResult<BasAlgorithmResult> result = clinicPartsClient.listAlgorithm(BeanUtil.convert(dto, PfClinicPartsParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addAlgorithm(BasAlgorithm dto) {
        CommonResult<Boolean> result = clinicPartsClient.addAlgorithm(BeanUtil.convert(dto, BasAlgorithmParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editAlgorithm(BasAlgorithm dto) {
        CommonResult<Boolean> result = clinicPartsClient.editAlgorithm(BeanUtil.convert(dto, BasAlgorithmParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delAlgorithm(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = clinicPartsClient.delAlgorithm(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<BasMedAsse> listAllPart() {
        CommonResult<List<BasMedAsseResult>> result = clinicPartsClient.listAllPart();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), BasMedAsse.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<BasEvaAsse> listAllSheet() {
        CommonResult<List<BasEvaAsseResult>> result = clinicPartsClient.listAllSheet();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), BasEvaAsse.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<BasAlgorithm> listAllAlgorithm() {
        CommonResult<List<BasAlgorithmResult>> result = clinicPartsClient.listAllAlgorithm();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), BasAlgorithm.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }
}

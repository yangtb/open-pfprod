package com.sm.pfprod.service.biz.disease.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.disease.BasDieParam;
import com.sm.open.core.facade.model.param.pf.biz.disease.PfDiseaseInfoParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.disease.BasDieResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.disease.DiseaseClient;
import com.sm.pfprod.model.dto.biz.disease.PfDiseaseInfoDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.BasDie;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.disease.PfDiseaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("pfDiseaseService")
public class PfDiseaseServiceImpl implements PfDiseaseService {

    @Resource
    private DiseaseClient diseaseClient;

    @Override
    public PageResult listDiseaseInfo(PfDiseaseInfoDto dto) {
        PfPageResult<BasDieResult> result = diseaseClient.listDiseaseInfo(BeanUtil.convert(dto, PfDiseaseInfoParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addDiseaseInfo(BasDie dto) {
        CommonResult<Boolean> result = diseaseClient.addDiseaseInfo(BeanUtil.convert(dto, BasDieParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editDiseaseInfo(BasDie dto) {
        CommonResult<Boolean> result = diseaseClient.editDiseaseInfo(BeanUtil.convert(dto, BasDieParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delDiseaseInfo(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = diseaseClient.delDiseaseInfo(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());

    }

}

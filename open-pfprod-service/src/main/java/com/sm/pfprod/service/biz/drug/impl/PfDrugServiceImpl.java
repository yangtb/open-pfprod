package com.sm.pfprod.service.biz.drug.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.biz.drug.BasDrugsParam;
import com.sm.open.core.facade.model.param.pf.biz.drug.PfDrugInfoParam;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.result.pf.biz.drug.BasDrugsResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.biz.drug.DrugClient;
import com.sm.pfprod.model.dto.biz.drug.PfDrugInfoDto;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.entity.BasDrugs;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.biz.drug.PfDrugService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("pfDrugService")
public class PfDrugServiceImpl implements PfDrugService {

    @Resource
    private DrugClient drugClient;


    @Override
    public PageResult listDrugInfo(PfDrugInfoDto dto) {
        PfPageResult<BasDrugsResult> result = drugClient.listDrugInfo(BeanUtil.convert(dto, PfDrugInfoParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addDrugInfo(BasDrugs dto) {
        CommonResult<Boolean> result = drugClient.addDrugInfo(BeanUtil.convert(dto, BasDrugsParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean editDrugInfo(BasDrugs dto) {
        CommonResult<Boolean> result = drugClient.editDrugInfo(BeanUtil.convert(dto, BasDrugsParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public boolean delDrugInfo(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = drugClient.delDrugInfo(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());

    }
}

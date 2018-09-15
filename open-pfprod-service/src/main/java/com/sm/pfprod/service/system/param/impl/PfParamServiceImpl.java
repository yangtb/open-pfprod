package com.sm.pfprod.service.system.param.impl;

import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.system.param.Param;
import com.sm.open.core.facade.model.param.pf.system.param.PfParamListParam;
import com.sm.open.core.facade.model.param.pf.system.param.SysParaPram;
import com.sm.open.core.facade.model.result.pf.system.param.SysParamResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.system.param.ParamClient;
import com.sm.pfprod.model.dto.system.param.ParamDto;
import com.sm.pfprod.model.dto.system.param.PfParamListDto;
import com.sm.pfprod.model.entity.SysParam;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.system.param.PfParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PfParamServiceImpl implements PfParamService {

    @Resource
    private ParamClient paramClient;

    @Override
    public PageResult<SysParam> listParams(ParamDto dto) {
        PfPageResult<SysParamResult> result = paramClient.listParams(BeanUtil.convert(dto, Param.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addParam(SysParam dto) {
        CommonResult<Boolean> result = paramClient.addParam(BeanUtil.convert(dto, SysParaPram.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean editParam(SysParam dto) {
        CommonResult<Boolean> result = paramClient.editParam(BeanUtil.convert(dto, SysParaPram.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean changeStatus(PfParamListDto dto) {
        CommonResult<Boolean> result = paramClient.changeStatus(BeanUtil.convert(dto, PfParamListParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }
}

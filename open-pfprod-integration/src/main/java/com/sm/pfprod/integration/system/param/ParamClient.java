package com.sm.pfprod.integration.system.param;

import com.sm.open.core.facade.model.param.pf.system.param.Param;
import com.sm.open.core.facade.model.param.pf.system.param.PfParamListParam;
import com.sm.open.core.facade.model.param.pf.system.param.SysParaPram;
import com.sm.open.core.facade.model.result.pf.system.param.SysParamResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.system.param.PfParamFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ParamClient {

    @Resource
    private PfParamFacade pfParamFacade;


    public PfPageResult<SysParamResult> listParams(Param param) {
        return pfParamFacade.listParams(param);
    }

    public CommonResult<Boolean> addParam(SysParaPram param) {
        return pfParamFacade.addParam(param);
    }

    public CommonResult<Boolean> editParam(SysParaPram param) {
        return pfParamFacade.editParam(param);
    }

    public CommonResult<Boolean> changeStatus(PfParamListParam param) {
        return pfParamFacade.changeStatus(param);
    }
}

package com.sm.pfprod.facade.param;

import com.sm.open.care.core.annotation.Loggable;
import com.sm.open.care.core.enums.Level;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.pfprod.model.dto.system.param.ParamDto;
import com.sm.pfprod.model.dto.system.param.PfParamListDto;
import com.sm.pfprod.model.entity.SysParam;
import com.sm.pfprod.model.param.PageParam;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.result.ResultFactory;
import com.sm.pfprod.service.system.param.PfParamService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("pfParamFacade")
public class PfParamFacadeImpl implements PfParamFacade {

    @Resource
    private PfParamService pfParamService;

    @Loggable(bizDec = "获取参数列表", level = Level.info)
    @Override
    public PageResult<SysParam> listParams(ParamDto dto) {
        try {
            PageParam.initPageDto(dto);
            return ResultFactory.initPageResultWithSuccess(pfParamService.count(dto), pfParamService.listParams(dto));
        } catch (Exception e) {
            return ResultFactory.initPageResultWithError(PfParamConstant.SELECT_PAGE_PARAM_ERROR,
                    PfParamConstant.SELECT_PAGE_PARAM_ERROR_MSG);
        }
    }

    @Loggable(bizDec = "新增参数", level = Level.info)
    @Override
    public boolean addParam(SysParam dto) {
        if (pfParamService.isExistParam(dto.getParamCode(), dto.getSysType())) {
            throw new BizRuntimeException(PfParamConstant.ADD_PARAM_ISEXIST, PfParamConstant.ADD_PARAM_ISEXIST_MSG);
        }
        return pfParamService.addParam(dto);
    }

    @Loggable(bizDec = "编辑参数", level = Level.info)
    @Override
    public boolean editParam(SysParam dto) {
        return pfParamService.editParam(dto);
    }

    @Loggable(bizDec = "停用、启用参数", level = Level.info)
    @Override
    public boolean changeStatus(PfParamListDto dto) {
        return pfParamService.changeStatus(dto.getList(), dto.getStatus());
    }
}

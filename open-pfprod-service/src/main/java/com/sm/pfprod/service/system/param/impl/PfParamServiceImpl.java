package com.sm.pfprod.service.system.param.impl;

import com.sm.open.care.core.enums.Status;
import com.sm.pfprod.dal.system.param.PfParamDao;
import com.sm.pfprod.model.dto.system.param.ParamDto;
import com.sm.pfprod.model.entity.SysParam;
import com.sm.pfprod.service.system.param.PfParamService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfParamService")
public class PfParamServiceImpl implements PfParamService {

    @Resource
    private PfParamDao pfParamDao;

    @Override
    public List<SysParam> listParams(ParamDto dto) {
        if (StringUtils.isBlank(dto.getStatus())) {
            dto.setStatus(Status.ENABLED.getCode());
        }
        return pfParamDao.listParams(dto);
    }

    @Override
    public Long count(ParamDto dto) {
        return pfParamDao.count(dto);
    }

    @Override
    public List<SysParam> listAllParam() {
        return pfParamDao.listAllParam();
    }

    @Override
    public boolean addParam(SysParam dto) {
        return pfParamDao.addParam(dto) == 1 ? true : false;
    }

    @Override
    public boolean editParam(SysParam dto) {
        return pfParamDao.editParam(dto) == 1 ? true : false;
    }

    @Override
    public boolean isExistParam(String paramCode, String sysType) {
        return pfParamDao.isExistParam(paramCode, sysType) >= 1 ? true : false;
    }

    @Override
    public boolean changeStatus(List<Long> list, String status) {
        return pfParamDao.changeStatus(list, status) >= 1 ? true : false;
    }
}

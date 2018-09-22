package com.sm.pfprod.service.system.org.impl;

import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.system.org.PfOrgParam;
import com.sm.open.core.facade.model.param.pf.system.org.SysOrgParam;
import com.sm.open.core.facade.model.result.pf.system.org.SysOrgResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.system.org.OrgClient;
import com.sm.pfprod.model.dto.common.PfBachChangeStatusDto;
import com.sm.pfprod.model.dto.system.org.PfOrgDto;
import com.sm.pfprod.model.entity.SysOrg;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.service.system.org.PfOrgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PfOrgServiceImpl implements PfOrgService {

    @Resource
    private OrgClient orgClient;

    @Override
    public PageResult listOrgs(PfOrgDto dto) {
        PfPageResult<SysOrgResult> result = orgClient.listOrgs(BeanUtil.convert(dto, PfOrgParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addOrg(SysOrg dto) {
        CommonResult<Boolean> result = orgClient.addOrg(BeanUtil.convert(dto, SysOrgParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean editOrg(SysOrg dto) {
        CommonResult<Boolean> result = orgClient.editOrg(BeanUtil.convert(dto, SysOrgParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean delOrg(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = orgClient.delOrg(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean authOrg(PfBachChangeStatusDto dto) {
        CommonResult<Boolean> result = orgClient.authOrg(BeanUtil.convert(dto, PfBachChangeStatusParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }
}

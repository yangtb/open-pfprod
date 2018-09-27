package com.sm.pfprod.service.system.org.impl;

import com.sm.open.care.core.exception.BizRuntimeException;
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
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<SysOrg> listAllOrg() {
        CommonResult<List<SysOrgResult>> result = orgClient.listAllOrg();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), SysOrg.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public Map<Long, String> listAllOrgMap() {
        CommonResult<List<SysOrgResult>> result = orgClient.listAllOrg();
        if (result != null && result.getIsSuccess()) {
            List<SysOrgResult> list = result.getContent();
            if (CollectionUtils.isEmpty(list)) {
                return MapUtils.EMPTY_MAP;
            }
            Map<Long, String> map = list.stream().collect(Collectors.toMap(SysOrgResult::getIdOrg, SysOrgResult::getName));
            return map;
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
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

    @Override
    public SysOrg selectOrgInfoById(Long idOrg) {
        CommonResult<SysOrgResult> result = orgClient.selectOrgInfoById(idOrg);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convert(result.getContent(), SysOrg.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }
}

package com.sm.pfprod.integration.system.org;

import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.system.org.*;
import com.sm.open.core.facade.model.result.pf.system.org.SysOrgResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.system.org.PfOrgFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OrgClient {

    @Resource
    private PfOrgFacade pfOrgFacade;


    public PfPageResult listOrgs(PfOrgParam param) {
        return pfOrgFacade.listOrgs(param);
    }

    public PfPageResult listAuthOrg(PfOrgAuthParam param) {
        return pfOrgFacade.listAuthOrg(param);
    }

    public CommonResult<List<SysOrgResult>> listAllOrg() {
        return pfOrgFacade.listAllOrg();
    }


    public CommonResult<Boolean> addOrg(SysOrgParam param) {
        return pfOrgFacade.addOrg(param);
    }

    public CommonResult<Boolean> editOrg(SysOrgParam param) {
        return pfOrgFacade.editOrg(param);
    }

    public CommonResult<Boolean> delOrg(PfBachChangeStatusParam param) {
        return pfOrgFacade.delOrg(param);
    }

    public CommonResult<Boolean> authOrg(PfBachOrgParam param) {
        return pfOrgFacade.authOrg(param);
    }

    public CommonResult<Boolean> rejectOrg(PfBachOrgParam param) {
        return pfOrgFacade.rejectOrg(param);
    }

    public CommonResult<SysOrgResult> selectOrgInfoById(Long idOrg) {
        return pfOrgFacade.selectOrgInfoById(idOrg);
    }

    public CommonResult<Boolean> activeOrg(SysOrgRegParam param) {
        return pfOrgFacade.activeOrg(param);
    }

}

package com.sm.pfprod.integration.system.org;

import com.sm.open.core.facade.model.param.pf.common.PfBachChangeStatusParam;
import com.sm.open.core.facade.model.param.pf.system.org.PfOrgParam;
import com.sm.open.core.facade.model.param.pf.system.org.SysOrgParam;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.system.org.PfOrgFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OrgClient {

    @Resource
    private PfOrgFacade pfOrgFacade;


    public PfPageResult listOrgs(PfOrgParam param) {
        return pfOrgFacade.listOrgs(param);
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

    public CommonResult<Boolean> authOrg(PfBachChangeStatusParam param) {
        return pfOrgFacade.authOrg(param);
    }

}

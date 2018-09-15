package com.sm.pfprod.integration.user.role;

import com.sm.open.core.facade.model.param.pf.user.role.PfRoleListParam;
import com.sm.open.core.facade.model.param.pf.user.role.PfRoleMenuParam;
import com.sm.open.core.facade.model.param.pf.user.role.PfRoleParam;
import com.sm.open.core.facade.model.param.pf.user.role.SysRoleParam;
import com.sm.open.core.facade.model.result.pf.user.role.PfRoleResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.user.role.PfRoleFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RoleClient {

    @Resource
    private PfRoleFacade pfRoleFacade;

    public PfPageResult<PfRoleResult> listRoles(PfRoleParam param) {
        return pfRoleFacade.listRoles(param);
    }

    public CommonResult<List<PfRoleResult>> list() {
        return pfRoleFacade.list();
    }

    public CommonResult<List<PfRoleResult>> listUserRole(Long userId) {
        return pfRoleFacade.listUserRole(userId);
    }

    public CommonResult<Boolean> addRole(SysRoleParam param) {
        return pfRoleFacade.addRole(param);
    }

    public CommonResult<Boolean> editRole(SysRoleParam param) {
        return pfRoleFacade.editRole(param);
    }

    public CommonResult<Boolean> delRole(List<Long> roles) {
        return pfRoleFacade.delRole(roles);
    }

    public CommonResult<Boolean> cancelRole(PfRoleListParam roles) {
        return pfRoleFacade.cancelRole(roles);
    }

    public CommonResult<Boolean> saveRoleMenu(PfRoleMenuParam param) {
        return pfRoleFacade.saveRoleMenu(param);
    }

}

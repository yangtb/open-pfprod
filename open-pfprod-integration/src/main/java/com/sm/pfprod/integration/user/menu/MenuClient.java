package com.sm.pfprod.integration.user.menu;

import com.sm.open.core.facade.model.param.pf.system.auth.SysFunctionParam;
import com.sm.open.core.facade.model.param.pf.system.menu.PfMenuListParam;
import com.sm.open.core.facade.model.param.pf.user.menu.MenuParam;
import com.sm.open.core.facade.model.result.pf.system.auth.SysFunctionResult;
import com.sm.open.core.facade.model.result.pf.user.menu.PfMenuResult;
import com.sm.open.core.facade.model.result.pf.user.menu.PfMenuZtreeResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.open.core.facade.pf.user.menu.PfMenuFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class MenuClient {

    @Resource
    private PfMenuFacade pfMenuFacade;


    public PfPageResult<SysFunctionResult> listMenus(MenuParam param) {
        return pfMenuFacade.listMenus(param);
    }

    public CommonResult<Boolean> addMenu(SysFunctionParam param) {
        return pfMenuFacade.addMenu(param);
    }

    public CommonResult<Boolean> changeStatusMenu(PfMenuListParam param) {
        return pfMenuFacade.changeStatusMenu(param);
    }

    public CommonResult<Boolean> updateMenu(SysFunctionParam param) {
        return pfMenuFacade.updateMenu(param);
    }

    public CommonResult<List<PfMenuZtreeResult>> listMenuTree() {
        return pfMenuFacade.listMenuTree();
    }

    public CommonResult<List<PfMenuZtreeResult>> listMenuRoleTree(Long roleId) {
        return pfMenuFacade.listMenuRoleTree(roleId);
    }

    public CommonResult<List<PfMenuResult>> listMyMenus(boolean isSuper, boolean isAnonymousUser, Long userId) {
        return pfMenuFacade.listMyMenus(isSuper, isAnonymousUser, userId);
    }
}

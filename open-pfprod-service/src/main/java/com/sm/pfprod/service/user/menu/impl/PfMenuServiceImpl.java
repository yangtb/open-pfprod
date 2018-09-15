package com.sm.pfprod.service.user.menu.impl;

import com.alibaba.fastjson.JSON;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.param.pf.system.auth.SysFunctionParam;
import com.sm.open.core.facade.model.param.pf.system.menu.PfMenuListParam;
import com.sm.open.core.facade.model.param.pf.user.menu.MenuParam;
import com.sm.open.core.facade.model.result.pf.system.auth.SysFunctionResult;
import com.sm.open.core.facade.model.result.pf.user.menu.PfMenuResult;
import com.sm.open.core.facade.model.result.pf.user.menu.PfMenuZtreeResult;
import com.sm.open.core.facade.model.rpc.CommonResult;
import com.sm.open.core.facade.model.rpc.PfPageResult;
import com.sm.pfprod.integration.user.menu.MenuClient;
import com.sm.pfprod.model.dto.system.menu.PfMenuListDto;
import com.sm.pfprod.model.dto.user.menu.MenuDto;
import com.sm.pfprod.model.entity.SysFunction;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.menu.PfMenuVo;
import com.sm.pfprod.model.vo.menu.PfMenuZtreeVo;
import com.sm.pfprod.service.user.menu.PfMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfMenuService")
public class PfMenuServiceImpl implements PfMenuService {

    @Resource
    private MenuClient menuClient;

    @Override
    public PageResult<SysFunction> listMenus(MenuDto dto) {
        PfPageResult<SysFunctionResult> result = menuClient.listMenus(BeanUtil.convert(dto, MenuParam.class));
        if (result == null) {
            return null;
        }
        return BeanUtil.convert(result, PageResult.class);
    }

    @Override
    public boolean addMenu(SysFunction dto) {
        CommonResult<Boolean> result = menuClient.addMenu(BeanUtil.convert(dto, SysFunctionParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean changeStatusMenu(PfMenuListDto dto) {
        CommonResult<Boolean> result = menuClient.changeStatusMenu(BeanUtil.convert(dto, PfMenuListParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public boolean updateMenu(SysFunction dto) {
        CommonResult<Boolean> result = menuClient.updateMenu(BeanUtil.convert(dto, SysFunctionParam.class));
        if (result != null && result.getIsSuccess()) {
            return result.getContent();
        }
        return false;
    }

    @Override
    public List<PfMenuZtreeVo> listMenuTree() {
        CommonResult<List<PfMenuZtreeResult>> result = menuClient.listMenuTree();
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfMenuZtreeVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfMenuZtreeVo> listMenuRoleTree(Long roleId) {
        CommonResult<List<PfMenuZtreeResult>> result = menuClient.listMenuRoleTree(roleId);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfMenuZtreeVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }

    @Override
    public List<PfMenuVo> listMyMenus(boolean isSuper, Long userId) {
        CommonResult<List<PfMenuResult>> result = menuClient.listMyMenus(isSuper, userId);
        if (result != null && result.getIsSuccess()) {
            return BeanUtil.convertList(result.getContent(), PfMenuVo.class);
        }
        throw new BizRuntimeException(result.getErrorCode(), result.getErrorDesc());
    }
}

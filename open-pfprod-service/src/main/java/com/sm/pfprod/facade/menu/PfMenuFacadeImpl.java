package com.sm.pfprod.facade.menu;

import com.sm.open.care.core.annotation.Loggable;
import com.sm.open.care.core.enums.Level;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.pfprod.model.dto.system.menu.PfMenuListDto;
import com.sm.pfprod.model.dto.user.menu.MenuDto;
import com.sm.pfprod.model.entity.SysFunction;
import com.sm.pfprod.model.entity.SysMenu;
import com.sm.pfprod.model.param.PageParam;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.result.ResultFactory;
import com.sm.pfprod.model.vo.menu.PfMenuVo;
import com.sm.pfprod.model.vo.menu.PfMenuZtreeVo;
import com.sm.pfprod.service.user.menu.PfMenuService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("pfMenuFacade")
public class PfMenuFacadeImpl implements PfMenuFacade {

    @Resource
    private PfMenuService pfMenuService;

    @Loggable(bizDec = "获取系统菜单列表", level = Level.info)
    @Override
    public PageResult<SysFunction> listMenus(MenuDto dto) {
        try {
            PageParam.initPageDto(dto);
            return ResultFactory.initPageResultWithSuccess(pfMenuService.countMenus(dto), pfMenuService.listMenus(dto));
        } catch (Exception e) {
            return ResultFactory.initPageResultWithError(PfMenuConstant.SELECT_PAGE_MENU_ERROR,
                    PfMenuConstant.SELECT_PAGE_MENU_ERROR_MSG);
        }
    }

    @Loggable(bizDec = "新增系统菜单", level = Level.info)
    @Override
    public boolean addMenu(SysFunction dto) {
        if (pfMenuService.isExistMenu(dto.getCode())) {
            throw new BizRuntimeException(PfMenuConstant.ADD_MENU_ISEXIST, PfMenuConstant.ADD_MENU_ISEXIST_MSG);
        }
        return pfMenuService.addMenu(dto);
    }

    @Loggable(bizDec = "修改系统菜单状态", level = Level.info)
    @Override
    public boolean changeStatusMenu(PfMenuListDto dto) {
        return pfMenuService.changeStatusMenu(dto.getList(), dto.getStatus());
    }

    @Loggable(bizDec = "修改系统菜单", level = Level.info)
    @Override
    public boolean updateMenu(SysFunction dto) {
        return pfMenuService.updateMenu(dto);
    }

    @Loggable(bizDec = "获取系统菜单【tree】", level = Level.info)
    @Override
    public List<PfMenuZtreeVo> listMenuTree() {
        return pfMenuService.listMenuTree();
    }

    @Loggable(bizDec = "获取角色拥有菜单", level = Level.info)
    @Override
    public List<PfMenuZtreeVo> listMenuRoleTree(Long roleId) {
        return pfMenuService.listMenuRoleTree(roleId);
    }

    @Loggable(bizDec = "获取用户菜单", level = Level.info)
    @Override
    public List<PfMenuVo> listMyMenus(boolean isSuper, Long userId) {
        return pfMenuService.listMyMenus(isSuper, userId);
    }


}

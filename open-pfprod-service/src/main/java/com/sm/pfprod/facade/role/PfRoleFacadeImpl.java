package com.sm.pfprod.facade.role;

import com.sm.open.care.core.annotation.Loggable;
import com.sm.open.care.core.enums.Level;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.pfprod.model.dto.user.role.PfRoleDto;
import com.sm.pfprod.model.dto.user.role.PfRoleListDto;
import com.sm.pfprod.model.dto.user.role.PfRoleMenuDto;
import com.sm.pfprod.model.entity.SysRole;
import com.sm.pfprod.model.param.PageParam;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.result.ResultFactory;
import com.sm.pfprod.model.vo.role.PfRoleVo;
import com.sm.pfprod.service.user.role.PfRoleService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component("pfRoleFacade")
public class PfRoleFacadeImpl implements PfRoleFacade {

    @Resource
    private PfRoleService pfRoleService;

    @Loggable(bizDec = "获取系统角色列表", level = Level.info)
    @Override
    public PageResult listRoles(PfRoleDto dto) {
        try {
            PageParam.initPageDto(dto);
            return ResultFactory.initPageResultWithSuccess(pfRoleService.countRoles(dto), pfRoleService.listRoles(dto));
        } catch (Exception e) {
            return ResultFactory.initPageResultWithError(PfRoleConstant.SELECT_PAGE_ROLE_LIST_ERROR,
                    PfRoleConstant.SELECT_PAGE_ROLE_LIST_ERROR_MSG);
        }
    }

    @Loggable(bizDec = "获取所有角色", level = Level.info)
    @Override
    public List<PfRoleVo> list() {
        return pfRoleService.list();
    }

    @Loggable(bizDec = "获取用户所有角色", level = Level.info)
    @Override
    public List<PfRoleVo> listUserRole(Long userId) {
        return pfRoleService.listUserRole(userId);
    }

    @Loggable(bizDec = "新增系统角色", level = Level.info)
    @Override
    public boolean addRole(SysRole dto) {
        if (pfRoleService.isExistRole(dto.getName())) {
            throw new BizRuntimeException(PfRoleConstant.ADD_ROLE_ISEXIST, PfRoleConstant.ADD_ROLE_ISEXIST_MSG);
        }
        return pfRoleService.addRole(dto);
    }

    @Loggable(bizDec = "编辑系统角色", level = Level.info)
    @Override
    public boolean editRole(SysRole dto) {
        return pfRoleService.editRole(dto);
    }

    @Loggable(bizDec = "删除角色", level = Level.info)
    @Override
    public boolean delRole(List<Long> roles) {
        return pfRoleService.delRole(roles);
    }

    @Loggable(bizDec = "作废/恢复角色", level = Level.info)
    @Override
    public boolean cancelRole(PfRoleListDto roles) {
        return pfRoleService.cancelRole(roles.getRoles());
    }

    @Loggable(bizDec = "保存角色菜单", level = Level.info)
    @Transactional
    @Override
    public boolean saveRoleMenu(PfRoleMenuDto dto) {
        pfRoleService.delRoleMenu(dto.getRoleMenus().get(0).getRoleId());
        pfRoleService.saveRoleMenu(dto.getRoleMenus());
        return true;
    }


}

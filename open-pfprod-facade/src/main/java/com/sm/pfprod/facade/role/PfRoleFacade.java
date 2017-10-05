package com.sm.pfprod.facade.role;

import com.sm.pfprod.model.dto.user.role.PfRoleDto;
import com.sm.pfprod.model.dto.user.role.PfRoleListDto;
import com.sm.pfprod.model.dto.user.role.PfRoleMenuDto;
import com.sm.pfprod.model.entity.SysRole;
import com.sm.pfprod.model.entity.SysRoleMenu;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.role.PfRoleVo;

import java.util.List;

/**
 * @ClassName: PfRoleFacade
 * @Description: 角色接口
 * @Author yangtongbin
 * @Date 2017/9/17 22:23
 */
public interface PfRoleFacade {

    /**
     * 角色列表
     *
     * @param dto
     * @return
     */
    PageResult listRoles(PfRoleDto dto);

    /**
     * 查询角色列表
     *
     * @return
     */
    List<PfRoleVo> list();

    /**
     * 查询用户角色
     *
     * @param userId 用户id
     * @return
     */
    List<PfRoleVo> listUserRole(Long userId);

    /**
     * 新增角色
     *
     * @param dto
     * @return
     */
    boolean addRole(SysRole dto);

    /**
     * 编辑菜单
     *
     * @param dto
     * @return
     */
    boolean editRole(SysRole dto);

    /**
     * 删除角色
     *
     * @param roles 角色ID
     * @return
     */
    boolean delRole(List<Long> roles);

    /**
     * 作废/恢复角色
     *
     * @param roles
     * @return
     */
    boolean cancelRole(PfRoleListDto roles);

    /**
     * 保存角色菜单
     *
     * @param dto
     */
    boolean saveRoleMenu(PfRoleMenuDto dto);
}

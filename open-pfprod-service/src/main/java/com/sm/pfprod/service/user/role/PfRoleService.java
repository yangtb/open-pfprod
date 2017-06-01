package com.sm.pfprod.service.user.role;

import com.github.pagehelper.PageInfo;
import com.sm.pfprod.model.dto.user.common.PfCommonDto;
import com.sm.pfprod.model.dto.user.role.PfRoleDto;
import com.sm.pfprod.model.entity.SysAuthority;
import com.sm.pfprod.model.entity.SysRole;
import com.sm.pfprod.model.entity.UserRole;
import com.sm.pfprod.model.vo.role.PfRoleVo;

import java.util.List;

/**
 * 用户角色
 */
public interface PfRoleService {

    /**
     * 获取角色列表
     * @return
     */
    PageInfo<PfRoleVo> listRoles(PfCommonDto dto);

    /**
     * 获取用户角色
     * @param userId
     * @return
     */
    List<UserRole> listRole(Long userId);

    /**
     * 新增菜单
     * @param dto
     * @return
     */
    boolean saveRole(PfRoleDto dto);

    /**
     * 修改菜单
     * @param dto
     * @return
     */
    boolean updateRole(PfRoleDto dto);

    /**
     * 删除菜单
     * @param roleId
     * @return
     */
    boolean delRole(Long roleId);

    /**
     * 获取用户有权限url
     * @param userId
     * @return
     */
    List<SysAuthority> selectAuthority(Long userId);
}

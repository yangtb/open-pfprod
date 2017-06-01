package com.sm.pfprod.dal.user.role;

import com.sm.pfprod.model.entity.SysAuthority;
import com.sm.pfprod.model.entity.SysRole;
import com.sm.pfprod.model.entity.UserRole;
import com.sm.pfprod.model.vo.role.PfRoleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfRoleDao {

    /**
     * 获取所有角色
     * @return
     */
    List<PfRoleVo> listRoles();

    /**
     * 获取用户角色
     * @param userId
     * @return
     */
    List<UserRole> listRole(@Param("userId") Long userId);

    /**
     * 新增菜单
     * @param dto
     * @return
     */
    Integer saveRole(SysRole dto);

    /**
     * 修改菜单
     * @param dto
     * @return
     */
    Integer updateRole(SysRole dto);

    /**
     * 删除菜单
     * @param roleId
     * @return
     */
    Integer delRole(@Param("roleId") Long roleId);

    /**
     * 获取用户有权限url
     * @param userId
     * @return
     */
    List<SysAuthority> selectAuthority(@Param("userId") Long userId);
}

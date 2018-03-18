package com.sm.pfprod.dal.user.menu;

import com.sm.pfprod.model.dto.user.menu.MenuDto;
import com.sm.pfprod.model.entity.SysFunction;
import com.sm.pfprod.model.entity.SysMenu;
import com.sm.pfprod.model.vo.menu.PfMenuZtreeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfMenuDao {

    /**
     * 获取菜单
     *
     * @param dto
     * @return
     */
    List<SysFunction> listMenus(MenuDto dto);

    /**
     * 菜单总数
     *
     * @param dto
     * @return
     */
    Long countMenus(MenuDto dto);

    /**
     * 新增菜单
     *
     * @param dto
     * @return
     */
    boolean addMenu(SysFunction dto);

    /**
     * 判断是否存在该菜单
     *
     * @param code 菜单code
     * @return
     */
    boolean isExistMenu(@Param("code") String code);

    /**
     * 删除菜单
     *
     * @return
     */
    int changeStatusMenu(@Param("list") List<Long> list,
                         @Param("status") String status);

    /**
     * 获取系统所有菜单
     *
     * @return
     */
    List<PfMenuZtreeVo> listSysMenus();

    /**
     * 获取角色拥有菜单
     *
     * @param roleId 角色ID
     * @return
     */
    List<PfMenuZtreeVo> listMenuRoleTree(@Param("roleId") Long roleId);

    /**
     * 编辑菜单
     *
     * @param dto
     * @return
     */
    boolean updateMenu(SysFunction dto);

    /**
     * 获取用户菜单
     *
     * @param userId 用户id
     * @return
     */
    List<SysFunction> listMyMenus(@Param("userId") Long userId);

    /**
     * 获取系统所有菜单
     *
     * @return
     */
    List<SysFunction> listAllMenus();
}

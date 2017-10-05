package com.sm.pfprod.facade.menu;

import com.sm.pfprod.model.dto.user.menu.MenuDto;
import com.sm.pfprod.model.entity.SysMenu;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.menu.PfMenuVo;
import com.sm.pfprod.model.vo.menu.PfMenuZtreeVo;

import java.util.List;

/**
 * @ClassName: PfMenuFacade
 * @Description: 菜单接口
 * @Author yangtongbin
 * @Date 2017/9/14 09:40
 */
public interface PfMenuFacade {

    /**
     * 获取菜单
     *
     * @return
     */
    PageResult<SysMenu> listMenus(MenuDto dto);

    /**
     * 新增菜单
     *
     * @param dto
     * @return
     */
    boolean addMenu(SysMenu dto);

    /**
     * 修改菜单状态
     *
     * @param dto
     * @return
     */
    boolean changeStatusMenu(MenuDto dto);

    /**
     * 编辑菜单
     *
     * @param dto
     * @return
     */
    boolean updateMenu(SysMenu dto);

    /**
     * 获取菜单[tree]
     *
     * @return
     */
    List<PfMenuZtreeVo> listMenuTree();

    /**
     * 获取角色拥有菜单
     *
     * @param roleId 角色id
     * @return
     */
    List<PfMenuZtreeVo> listMenuRoleTree(Long roleId);

    /**
     * 获取用户菜单
     *
     * @param userId 用户id
     * @return
     */
    List<PfMenuVo> listMyMenus(Long userId);

}
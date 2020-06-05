package com.sm.pfprod.service.user.menu;

import com.sm.pfprod.model.dto.system.menu.PfMenuListDto;
import com.sm.pfprod.model.dto.user.menu.MenuDto;
import com.sm.pfprod.model.entity.SysFunction;
import com.sm.pfprod.model.result.PageResult;
import com.sm.pfprod.model.vo.menu.PfMenuVo;
import com.sm.pfprod.model.vo.menu.PfMenuZtreeVo;

import java.util.List;

/**
 * @ClassName: PfMenuService
 * @Description: 菜单接口
 * @Author yangtongbin
 * @Date 2017/9/14 10:24
 */
public interface PfMenuService {

    /**
     * 获取菜单
     *
     * @return
     */
    PageResult<SysFunction> listMenus(MenuDto dto);

    /**
     * 新增菜单
     *
     * @param dto
     * @return
     */
    boolean addMenu(SysFunction dto);

    /**
     * 修改菜单状态
     *
     * @param dto
     * @return
     */
    boolean changeStatusMenu(PfMenuListDto dto);

    /**
     * 编辑菜单
     *
     * @param dto
     * @return
     */
    boolean updateMenu(SysFunction dto);

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
     * @param isSuper         是否是超级管理员
     * @param isAnonymousUser 匿名用户
     * @param userId          用户id
     * @return
     */
    List<PfMenuVo> listMyMenus(boolean isSuper,
                               boolean isAnonymousUser,
                               Long userId);
}

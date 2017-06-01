package com.sm.pfprod.service.user.menu;

import com.sm.pfprod.model.dto.common.User;
import com.sm.pfprod.model.dto.user.menu.MenuDto;
import com.sm.pfprod.model.entity.SysMenu;
import com.sm.pfprod.model.vo.menu.PfMenuVo;

import java.util.List;

/**
 * 菜单接口
 */
public interface PfMenuService {


    /**
     * 获取用户菜单
     * @param user
     * @return
     */
    List<PfMenuVo> listMyMenus(User user);

    /**
     * 获取菜单
     * @return
     */
    List<SysMenu> listMenus(MenuDto dto);

    /**
     * 获取菜单[tree]
     * @return
     */
    List<PfMenuVo> listMenuTree();

    /**
     * 新增菜单
     * @param dto
     * @return
     */
    boolean addMenu(SysMenu dto);

    /**
     * 编辑菜单
     * @param dto
     * @return
     */
    boolean updateMenu(SysMenu dto);

    /**
     * 删除菜单
     * @param dto
     * @return
     */
    boolean delMenu(MenuDto dto);
}

package com.sm.pfprod.dal.user.menu;

import com.sm.pfprod.model.dto.user.menu.MenuDto;
import com.sm.pfprod.model.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PfMenuDao {

    /**
     * 获取用户菜单
     * @param userId
     * @return
     */
    List<SysMenu> listMyMenus(@Param("userId") Long userId);

    /**
     * 获取菜单
     * @param dto
     * @return
     */
    List<SysMenu> listMenus(MenuDto dto);

    /**
     * 获取系统所有菜单
     * @return
     */
    List<SysMenu> listSysMenus();

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

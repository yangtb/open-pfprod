package com.sm.pfprod.service.user.menu.impl;

import com.sm.pfprod.model.entity.SysMenu;
import com.sm.pfprod.model.vo.menu.PfBaseMenuVo;
import com.sm.pfprod.model.vo.menu.PfMenuVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PfMenuHelper {

    public static List<PfMenuVo> convertMenu(List<SysMenu> sysMenus){
        if (CollectionUtils.isEmpty(sysMenus)) {
            return null;
        }
        /* 组装tree型菜单 */
        Map<Long, PfMenuVo> menuMap = new HashMap<Long, PfMenuVo>();
        List<SysMenu> childrenMenu = new ArrayList<SysMenu>();
        PfMenuVo pfMenuVo = null;
        for (SysMenu sysMenu : sysMenus) {
            if (sysMenu.getLevel() == 1) {
                pfMenuVo = new PfMenuVo();
                BeanUtils.copyProperties(sysMenu, pfMenuVo);
                menuMap.put(sysMenu.getMenuId(), pfMenuVo);
            } else {
                childrenMenu.add(sysMenu);
            }
        }

        PfBaseMenuVo pfBaseMenuVo = null;
        for (SysMenu subMenu : childrenMenu) {
            if (subMenu.getParentId() != null && menuMap.containsKey(subMenu.getParentId())) {
                PfMenuVo menu = menuMap.get(subMenu.getParentId());
                if (menu.getChild() == null) {
                    menu.setChild(new ArrayList<PfBaseMenuVo>());
                }
                pfBaseMenuVo = new PfBaseMenuVo();
                BeanUtils.copyProperties(subMenu, pfBaseMenuVo);
                menu.getChild().add(pfBaseMenuVo);
            }
        }
        return new ArrayList<PfMenuVo>(menuMap.values());
    }
}

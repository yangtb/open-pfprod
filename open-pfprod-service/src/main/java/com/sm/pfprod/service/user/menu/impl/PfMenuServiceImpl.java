package com.sm.pfprod.service.user.menu.impl;

import com.sm.pfprod.dal.user.menu.PfMenuDao;
import com.sm.pfprod.model.dto.user.menu.MenuDto;
import com.sm.pfprod.model.entity.SysFunction;
import com.sm.pfprod.model.vo.menu.PfBaseMenuVo;
import com.sm.pfprod.model.vo.menu.PfMenuVo;
import com.sm.pfprod.model.vo.menu.PfMenuZtreeVo;
import com.sm.pfprod.service.user.menu.PfMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("pfMenuService")
public class PfMenuServiceImpl implements PfMenuService {

    @Resource
    private PfMenuDao pfMenuDao;

    @Override
    public List<SysFunction> listMenus(MenuDto dto) {
        return pfMenuDao.listMenus(dto);
    }

    @Override
    public Long countMenus(MenuDto dto) {
        return pfMenuDao.countMenus(dto);
    }

    @Override
    public boolean addMenu(SysFunction dto) {
        return pfMenuDao.addMenu(dto);
    }

    @Override
    public boolean isExistMenu(String code) {
        return pfMenuDao.isExistMenu(code);
    }

    @Override
    public boolean changeStatusMenu(List<Long> list, String status) {
        return pfMenuDao.changeStatusMenu(list, status) == 1 ? true : false;
    }

    @Override
    public boolean updateMenu(SysFunction dto) {
        return pfMenuDao.updateMenu(dto);
    }

    @Override
    public List<PfMenuZtreeVo> listMenuTree() {
        return pfMenuDao.listSysMenus();
    }

    @Override
    public List<PfMenuZtreeVo> listMenuRoleTree(Long roleId) {
        return pfMenuDao.listMenuRoleTree(roleId);
    }

    @Override
    public List<PfMenuVo> listMyMenus(boolean isSuper, Long userId) {
        List<SysFunction> menus = isSuper ? pfMenuDao.listAllMenus() : pfMenuDao.listMyMenus(userId);
        List<PfMenuVo> parentMenuList = new ArrayList<>();
        PfMenuVo pfMenuVo;
        for (SysFunction sysMenu : menus) {
            pfMenuVo = new PfMenuVo();
            if (sysMenu.getLevel() == 1) {
                pfMenuVo.setParentCode(sysMenu.getCode());
                pfMenuVo.setParentMenuName(sysMenu.getName());
                pfMenuVo.setParentImg(sysMenu.getIconSource());
                pfMenuVo.setParentUrl(sysMenu.getFunctionUrl());
                parentMenuList.add(pfMenuVo);
            }
        }

        List<PfBaseMenuVo> groupList;
        PfBaseMenuVo pfBaseMenuVo;
        for (PfMenuVo parentMenuVo : parentMenuList) {
            groupList = new ArrayList<>();
            for (SysFunction sysMenu : menus) {
                if (sysMenu.getLevel() == 1) {
                    continue;
                }
                if (sysMenu.getParentCode().equals(parentMenuVo.getParentCode())) {
                    pfBaseMenuVo = new PfBaseMenuVo();
                    pfBaseMenuVo.setMenuId(sysMenu.getId());
                    pfBaseMenuVo.setName(sysMenu.getName());
                    pfBaseMenuVo.setUrl(sysMenu.getFunctionUrl());
                    pfBaseMenuVo.setImg(sysMenu.getIconSource());
                    groupList.add(pfBaseMenuVo);
                }
            }
            parentMenuVo.setGroupList(groupList);
        }
        return parentMenuList;
    }

}

package com.sm.pfprod.service.user.menu.impl;

import com.sm.pfprod.dal.user.menu.PfMenuDao;
import com.sm.pfprod.model.dto.common.UserDto;
import com.sm.pfprod.model.dto.user.menu.MenuDto;
import com.sm.pfprod.model.entity.SysFunction;
import com.sm.pfprod.model.entity.SysMenu;
import com.sm.pfprod.model.enums.AdminRoleType;
import com.sm.pfprod.model.vo.menu.PfBaseMenuVo;
import com.sm.pfprod.model.vo.menu.PfMenuVo;
import com.sm.pfprod.model.vo.menu.PfMenuZtreeVo;
import com.sm.pfprod.service.user.menu.PfMenuService;
import org.apache.commons.lang3.StringUtils;
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
    public List<PfMenuVo> listMyMenus(Long userId) {
        List<SysMenu> menus = pfMenuDao.listMyMenus(userId);
        List<PfMenuVo> parentMenuList = new ArrayList<>();
        PfMenuVo pfMenuVo;
        for (SysMenu sysMenu : menus) {
            pfMenuVo = new PfMenuVo();
            if (sysMenu.getLevel() == 1) {
                pfMenuVo.setParentMenuId(sysMenu.getMenuId());
                pfMenuVo.setParentMenuName(sysMenu.getName());
                pfMenuVo.setParentImg(sysMenu.getImg());
                pfMenuVo.setParentUrl(sysMenu.getUrl());
                parentMenuList.add(pfMenuVo);
            }
        }

        List<PfBaseMenuVo> groupList;
        PfBaseMenuVo pfBaseMenuVo;
        for (PfMenuVo parentMenuVo : parentMenuList) {
            groupList = new ArrayList<>();
            for (SysMenu sysMenu : menus) {
                if (sysMenu.getParentId() == parentMenuVo.getParentMenuId()) {
                    pfBaseMenuVo = new PfBaseMenuVo();
                    pfBaseMenuVo.setMenuId(sysMenu.getMenuId());
                    pfBaseMenuVo.setName(sysMenu.getName());
                    pfBaseMenuVo.setUrl(sysMenu.getUrl());
                    pfBaseMenuVo.setImg(sysMenu.getImg());
                    groupList.add(pfBaseMenuVo);
                }
            }
            parentMenuVo.setGroupList(groupList);
        }
        return parentMenuList;
    }

   /* @Override
    public List<PfMenuVo> listMyMenus() {
        List<SysMenu> sysMenus = null;
        *//* 如果是超级管理员 *//*
        if (user.getRoleType().equals(AdminRoleType.SUNPER.getValue())) {
            //sysMenus = pfMenuDao.listSysMenus();
        } else {
            sysMenus =
        }
    }*/


}

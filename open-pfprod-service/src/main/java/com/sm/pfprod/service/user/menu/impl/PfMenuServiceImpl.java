package com.sm.pfprod.service.user.menu.impl;

import com.sm.pfprod.dal.user.menu.PfMenuDao;
import com.sm.pfprod.model.dto.common.UserDto;
import com.sm.pfprod.model.dto.user.menu.MenuDto;
import com.sm.pfprod.model.entity.SysMenu;
import com.sm.pfprod.model.enums.AdminRoleType;
import com.sm.pfprod.model.vo.menu.PfMenuVo;
import com.sm.pfprod.model.vo.menu.PfMenuZtreeVo;
import com.sm.pfprod.service.user.menu.PfMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pfMenuService")
public class PfMenuServiceImpl implements PfMenuService {

    @Resource
    private PfMenuDao pfMenuDao;

    @Override
    public List<SysMenu> listMenus(MenuDto dto) {
        return pfMenuDao.listMenus(dto);
    }

    @Override
    public Long countMenus(MenuDto dto) {
        return pfMenuDao.countMenus(dto);
    }

    @Override
    public boolean addMenu(SysMenu dto) {
        return pfMenuDao.addMenu(dto);
    }

    @Override
    public boolean isExistMenu(Long menuId) {
        return pfMenuDao.isExistMenu(menuId);
    }

    @Override
    public boolean changeStatusMenu(MenuDto dto) {
        return pfMenuDao.changeStatusMenu(dto) == 1 ? true : false;
    }

    @Override
    public boolean updateMenu(SysMenu dto) {
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
        return pfMenuDao.listMyMenus(userId);
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

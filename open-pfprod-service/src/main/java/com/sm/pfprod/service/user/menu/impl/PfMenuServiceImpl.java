package com.sm.pfprod.service.user.menu.impl;


import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.utils.Assert;
import com.sm.open.care.core.ErrorCode;
import com.sm.pfprod.dal.user.menu.PfMenuDao;
import com.sm.pfprod.model.dto.common.User;
import com.sm.pfprod.model.dto.user.menu.MenuDto;
import com.sm.pfprod.model.entity.SysMenu;
import com.sm.pfprod.model.enums.AdminRoleType;
import com.sm.pfprod.model.vo.menu.PfMenuVo;
import com.sm.pfprod.service.user.menu.PfMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;

@Service("pfMenuService")
public class PfMenuServiceImpl implements PfMenuService {

    @Resource
    private PfMenuDao pfMenuDao;

    @Override
    public List<PfMenuVo> listMyMenus(User user) {
        List<SysMenu> sysMenus = null;
        /* 如果是超级管理员 */
        if (user.getRoleType().equals(AdminRoleType.SUNPER.getValue())) {
            sysMenus = pfMenuDao.listSysMenus();
        } else {
            sysMenus = pfMenuDao.listMyMenus(user.getUserId());
        }
        return PfMenuHelper.convertMenu(sysMenus);
    }

    @Override
    public List<SysMenu> listMenus(MenuDto dto) {
        return pfMenuDao.listMenus(dto);
    }

    @Override
    public List<PfMenuVo> listMenuTree() {
        List<SysMenu> sysMenus = pfMenuDao.listSysMenus();
        return PfMenuHelper.convertMenu(sysMenus);
    }

    @Override
    public boolean addMenu(SysMenu dto) {
        /* 参数校验 */
        Assert.isNull(dto.getLevel(), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "level"));
        Assert.isTrue(StringUtils.isNotBlank(dto.getUrl()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "url"));
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "name"));
        Assert.isNull(dto.getSort(), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "sort"));

        return pfMenuDao.addMenu(dto);
    }

    @Override
    public boolean updateMenu(SysMenu dto) {
        /* 参数校验 */
        Assert.isNull(dto.getLevel(), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "level"));
        Assert.isTrue(StringUtils.isNotBlank(dto.getUrl()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "url"));
        Assert.isTrue(StringUtils.isNotBlank(dto.getName()), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "name"));
        Assert.isNull(dto.getSort(), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "sort"));

        return pfMenuDao.updateMenu(dto);
    }

    @Override
    public boolean delMenu(MenuDto dto) {
         /* 参数校验 */
        Assert.isNull(dto.getMenuId(), ErrorCode.ERROR_GENERAL_110001, MessageFormat.format(ErrorMessage.MESSAGE_GENERAL_110001, "menu_id"));
        return pfMenuDao.delMenu(dto);
    }

}

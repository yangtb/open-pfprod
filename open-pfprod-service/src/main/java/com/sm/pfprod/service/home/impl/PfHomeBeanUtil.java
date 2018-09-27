package com.sm.pfprod.service.home.impl;

import com.sm.open.care.core.utils.BeanUtil;
import com.sm.open.core.facade.model.result.pf.home.PfHomeResult;
import com.sm.pfprod.model.entity.SysOrg;
import com.sm.pfprod.model.vo.home.PfHomeVo;
import com.sm.pfprod.model.vo.menu.PfMenuVo;

public class PfHomeBeanUtil {

    public static PfHomeVo convert(PfHomeResult result) {
        if (result == null) {
            return null;
        }
        PfHomeVo homeVo = new PfHomeVo();
        homeVo.setTopMenus(BeanUtil.convertList(result.getTopMenus(), PfMenuVo.class));
        homeVo.setLeftMenus(BeanUtil.convertList(result.getLeftMenus(), PfMenuVo.class));
        homeVo.setUsername(result.getUsername());
        homeVo.setSysOrg(BeanUtil.convert(result.getSysOrg(), SysOrg.class));
        homeVo.setAnonymousUser(result.isAnonymousUser());
        homeVo.setWebsiteName(result.getWebsiteName());
        homeVo.setWebsiteCopyright(result.getWebsiteCopyright());
        return homeVo;
    }
}

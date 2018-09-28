package com.sm.pfprod.web.util;

import com.sm.open.care.core.enums.YesOrNoNum;
import com.sm.pfprod.web.security.CurrentUserUtils;
import com.sm.pfprod.web.security.SecurityContext;
import com.sm.pfprod.web.security.User;

public class SysUserAuthUtils {

    /**
     * 拥有平台权限或者超级管理员
     *
     * @return
     */
    public static boolean isPlatOrSuper() {
        User user = CurrentUserUtils.getCurrentUser();
        if (SecurityContext.hasRole("ROLE_SUPER") || user.getFgPlat().equals(YesOrNoNum.YES.getCode())) {
            return true;
        } else {
            return false;
        }
    }
}

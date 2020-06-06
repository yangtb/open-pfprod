package com.sm.pfprod.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.Set;

/**
 * @ClassName: SecurityContext
 * @Description: 当前登录用户上下文
 */
public class SecurityContext {
    private static final Logger logger = LoggerFactory.getLogger(SecurityContext.class);

    public static final String ANONYMOUS_USER = "anonymousUser";

    /**
     * 获取当前用户的认证对象
     *
     * @return
     */
    public static Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 当前用户是否认证过
     *
     * @return
     */
    public static boolean isAuthenticated() {
        return getCurrentAuthentication().isAuthenticated();
    }

    /**
     * 获得当前用户
     * User currentUser = SecurityContext.currentUser();
     *
     * @return User 当前用户
     */
    public static User currentUser() {
        Object principal = getCurrentAuthentication().getPrincipal();
        if (ANONYMOUS_USER.equals(principal)) {
            if (logger.isInfoEnabled()) {
                logger.info("匿名用户");
            }
            return null;
        }
        User currentUser = null;
        try {
            currentUser = (User) principal;
        } catch (ClassCastException e) {
            logger.error("获取当前用户信息转换异常", e);
            return null;
        }
        return currentUser;
    }

    /**
     * 判断当前用户是否匿名用户
     *
     * @return
     */
    public static boolean isAnonymousUser() {
        Object principal = getCurrentAuthentication().getPrincipal();
        return ANONYMOUS_USER.equals(principal);
    }

    /**
     * 获取当前用户拥有的权限集合
     *
     * @return
     */
    public static Set<String> getAuthorities() {
        Object principal = getCurrentAuthentication().getPrincipal();
        if (ANONYMOUS_USER.equals(principal)) {
            return null;
        }
        PfUserDetails user = (PfUserDetails) principal;
        Map<String, GrantedAuthority> authorityMap = user.getAuthorityMap();
        if (authorityMap == null || authorityMap.size() <= 0) {
            return null;
        }
        return authorityMap.keySet();
    }

    /**
     * 判断当前用户是否有给定的权限
     * 注意：此方法在应用层使用，如在Controller,Service中使用
     * if ( SecurityContext.authorize( "ROLE_ADMIN" ) )
     *
     * @param authority
     * @return
     */
    public static boolean hasRole(final String authority) {
        Object principal = getCurrentAuthentication().getPrincipal();
        if (ANONYMOUS_USER.equals(principal)) {
            return false;
        }
        PfUserDetails user = (PfUserDetails) principal;
        Map<String, GrantedAuthority> authorityMap = user.getAuthorityMap();
        if (authorityMap == null || authorityMap.size() <= 0) {
            return false;
        }
        return authorityMap.get(authority) != null;
    }

}

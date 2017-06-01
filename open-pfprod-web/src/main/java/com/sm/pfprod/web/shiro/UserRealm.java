package com.sm.pfprod.web.shiro;

import com.sm.pfprod.model.entity.SysAuthority;
import com.sm.pfprod.model.entity.UserInfo;
import com.sm.pfprod.service.user.login.PfUserService;
import com.sm.pfprod.service.user.role.PfRoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Resource
    private PfUserService pfUserService;

    @Resource
    private PfRoleService pfRoleService;

    /**
     * 授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserInfo userInfo = (UserInfo) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 查询用户角色
        //authorizationInfo.setRoles(null);
        /* 查询角色对应url权限 */
        List<SysAuthority> sysAuthorities = pfRoleService.selectAuthority(userInfo.getUserId());
        Set<String> urls = new HashSet<String>();
        for (SysAuthority sysAuthority: sysAuthorities) {
            urls.add(sysAuthority.getAuthorityName());
        }
        info.setStringPermissions(urls);
        // clearCachedAuthorizationInfo

        return info;
    }

    /**
     * 认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1. 把AuthenticationToken转换为UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        // 2. 从UsernamePasswordToken中获取email
        String userName = (String) upToken.getPrincipal();
        // 3. 若用户不存在，抛出UnknownAccountException异常
        UserInfo user = pfUserService.selectUser(userName);
        if (user == null)
            throw new UnknownAccountException("用户不存在");
        // 判断帐号是否锁定
        if (Boolean.TRUE.equals(user.getIsBlock())) {
            // 抛出 帐号锁定异常
            throw new LockedAccountException("账户已被锁定");
        }

        /**
         * 4. 根据用户的情况，来构建AuthenticationInfo对象并返回，通常使用的实现类为SimpleAuthenticationInfo
         * 以下信息从数据库中获取
         *（1）principal：认证的实体信息，可以是email，也可以是数据表对应的用户的实体类对象
         *（2）credentials：密码
         *（3）realmName：当前realm对象的name，调用父类的getName()方法即可
         *（4）盐值：取用户信息中唯一的字段来生成盐值，避免由于两个用户原始密码相同，加密后的密码也相同
         */
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getForSalt());
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, getName());
        return info;
    }
}

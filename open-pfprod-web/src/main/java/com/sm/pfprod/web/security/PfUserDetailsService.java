package com.sm.pfprod.web.security;

import com.alibaba.fastjson.JSON;
import com.sm.pfprod.model.entity.UserInfo;
import com.sm.pfprod.service.user.login.PfUserService;
import com.sm.pfprod.service.user.security.AuthorityService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @ClassName: PfUserDetailsService
 * @Description: 用户授权用户详情服务
 * @Author yangtongbin
 * @Date 2017/9/2 22:42
 */
public class PfUserDetailsService implements UserDetailsService, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(PfUserDetailsService.class);

    private AuthorityService authorityService;

    private PfUserService pfUserService;

    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private Map<String, CustomUser> customUserMap;

    protected List<PfGrantedAuthority> customAuthorities = Arrays.asList(new PfGrantedAuthority("ROLE_USER"));

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        Validate.notEmpty(username);
        UserInfo user = pfUserService.selectUser(username);
        CustomUser customUser = customUserMap != null ? customUserMap.get(username) : null;
        PfUserDetails userDetails;
        Set<GrantedAuthority> dbAuthsSet = new HashSet<>();
        if (user == null || user.getUserId() == null || user.getUserId() <= 0 || StringUtils.isBlank(user.getUsername())) {
            String logMsg = "查询用户'" + username + "'返回结果为空。" + JSON.toJSONString(user);
            String exceptionMsg = messages.getMessage("findUserByUserName({0})", new Object[]{username}, "用户:" + username + "不存在！" + JSON.toJSONString(user));
            if (customUser == null || StringUtils.isBlank(customUser.getUsername())) {
                logger.debug(logMsg);
                throw new UsernameNotFoundException(exceptionMsg);
            } else {
                logMsg = logMsg + "将尝试使用自定义用户登录认证";
                logger.warn(logMsg, new UsernameNotFoundException(exceptionMsg));
                userDetails = customUser;
                userDetails.setAccountNonExpired(true);
                userDetails.setCredentialsNonExpired(true);
                dbAuthsSet.addAll(customUser.getAuthorities());
            }
        } else {
            userDetails = this.createUserDetails(user);
            if (customUser != null && !StringUtils.isBlank(customUser.getUsername())) {
                dbAuthsSet.addAll(customUser.getAuthorities());
            }
        }
        dbAuthsSet.addAll(customAuthorities);

        List<String> authorities = authorityService.findAuthoritiesByUserId(user.getUserId(), user.getRoleType());
        if (CollectionUtils.isNotEmpty(authorities)) {
            for (Iterator<String> iterator = authorities.iterator(); iterator.hasNext(); ) {
                String authority = iterator.next();
                dbAuthsSet.add(new PfGrantedAuthority(authority));
            }
        }

        List<GrantedAuthority> dbAuthList = new ArrayList<>(dbAuthsSet);
        if (CollectionUtils.size(dbAuthList) == 0) {
            logger.debug("用户'" + username + "'没有权限资源，该用户将被当做‘用户不存在’处理");
            throw new UsernameNotFoundException(messages.getMessage("PfUserDetailsService.noAuthority", new Object[]{username}, "用户" + username + "没有被授予权限资源"));
        }
        userDetails.setAuthorities(dbAuthList);
        return userDetails;
    }

    protected PfUserDetails createUserDetails(UserInfo user) {
        PfUserDetails ud = new PfUserDetails();
        BeanUtils.copyProperties(user, ud);
        ud.setAccountNonExpired(true);
        ud.setAccountNonLocked(true);
        ud.setCredentialsNonExpired(true);
        return ud;
    }

    public void setCustomAuthoritiesAsString(String customAuthoritiesAsString) {
        if (StringUtils.isBlank(customAuthoritiesAsString)) {
            customAuthorities = Collections.emptyList();
        } else {
            customAuthoritiesAsString = customAuthoritiesAsString.trim();
            Validate.isTrue(customAuthoritiesAsString.matches("^\\s*ROLE_[a-zA-Z0-9-_]+([,| |\t]+ROLE_[a-zA-Z0-9-_]+)*\\s*$"));
            String[] auths = customAuthoritiesAsString.split("[,| |\t]+");
            customAuthorities = new ArrayList<>();
            for (String auth : auths) {
                customAuthorities.add(new PfGrantedAuthority(auth.trim()));
            }
        }
    }

    public void setCustomAuthorities(List<PfGrantedAuthority> customAuthorities) {
        this.customAuthorities = customAuthorities;
    }

    public void setCustomUserMap(Map<String, CustomUser> customUserMap) {
        this.customUserMap = customUserMap;
    }

    public void setCustomUsers(List<CustomUser> customUsers) {
        if (CollectionUtils.isEmpty(customUsers)) {
            return;
        }
        this.customUserMap = new HashMap<>();
        for (CustomUser user : customUsers) {
            if (!StringUtils.isBlank(user.getUsername())) {
                customUserMap.put(user.getUsername().trim(), user);
            }
        }
    }


    public void setAuthorityService(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    public void setUserService(PfUserService pfUserService) {
        this.pfUserService = pfUserService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.authorityService, "AuthorityService注入不能为空");
        Assert.notNull(this.pfUserService, "PfUserService注入不能为空");
    }

}

package com.sm.pfprod.web.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.util.Assert;

/**
 * @ClassName: UsernamePasswordAuthenticationProvider
 * @Description: 用户名密码认证器
 * @Author yangtongbin
 * @Date 2017/9/2 22:49
 */
public class UsernamePasswordAuthenticationProvider extends PfAbstractUserDetailsAuthenticationProvider {

    private SaltSource saltSource;

    private UserDetailsService userDetailsService;

    public UsernamePasswordAuthenticationProvider() {
    }

    /**
     * <p>Title: additionalAuthenticationChecks</p>
     * <p>Description: 用户密码认证</p>
     *
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException
     * @see PfAbstractUserDetailsAuthenticationProvider#additionalAuthenticationChecks(org.springframework.security.core.userdetails.UserDetails, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)
     */
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        Object salt = null;
        if (this.saltSource != null) {
            salt = this.saltSource.getSalt(userDetails);
        }
        if (authentication.getCredentials() == null) {
            logger.debug("认证失败: 没有提供密码");
            throw new BadCredentialsException(
                    messages.getMessage("PfAbstractUserDetailsAuthenticationProvider.badCredentials", "密码错误"));
        }
        //当前待认证的密码
        String presentedPassword = authentication.getCredentials().toString();
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder(salt.toString());
        if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            logger.debug("认证失败: 当前密码与存储用户中的密码值不能匹配");
            throw new BadCredentialsException(
                    messages.getMessage("PfAbstractUserDetailsAuthenticationProvider.badCredentials", "密码错误"));
        }
    }

    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "UserDetailsService注入不能为空");
    }

    /**
     * <p>Title: retrieveUser</p>
     * <p>Description: 根据用户名检索存储中的用户信息</p>
     *
     * @param username
     * @param authentication
     * @return
     * @throws AuthenticationException
     * @see PfAbstractUserDetailsAuthenticationProvider#retrieveUser(String, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)
     */
    protected final UserDetails retrieveUser(String username,
                                             UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        UserDetails loadedUser;
        try {
            loadedUser = this.getUserDetailsService().loadUserByUsername(username);
        } catch (UsernameNotFoundException notFound) {
            throw notFound;
        } catch (Exception repositoryProblem) {
            throw new InternalAuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }
        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException(
                    "认证内部异常：UserDetailsService中接口返回空用户, UserDetailsService接口编码存在问题");
        }
        return loadedUser;
    }

    public void setSaltSource(SaltSource saltSource) {
        this.saltSource = saltSource;
    }

    protected SaltSource getSaltSource() {
        return saltSource;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

}

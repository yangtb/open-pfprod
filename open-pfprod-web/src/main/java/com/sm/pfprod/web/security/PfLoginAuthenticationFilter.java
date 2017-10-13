package com.sm.pfprod.web.security;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.utils.rsa.RSAEncrypt;
import com.sm.open.care.core.utils.rsa.RsaKeyPair;
import com.sm.pfprod.web.security.rsa.RsaKeyPairQueue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: PfLoginAuthenticationFilter
 * @Description: 登录请求处理
 * @Author yangtongbin
 * @Date 2017/9/1 23:07
 */
public class PfLoginAuthenticationFilter extends PfAbstractAuthenticationProcessingFilter {

    private static final Logger logger = LoggerFactory.getLogger(PfLoginAuthenticationFilter.class);

    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private boolean postOnly = true;

    private RsaKeyPairQueue rsaKeyPairQueue;

    public PfLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("不支持" + request.getMethod() + "认证请求方法！");
        }

        //TODO 用户名的分发校验
        //TODO 用户密码的解密及基础校验
        //TODO 其他参数的校验处理

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (StringUtils.isBlank(username)) {
            throw new AuthenticationServiceException("用户名不能为空！");
        }
        if (StringUtils.isBlank(password)) {
            throw new AuthenticationServiceException("密码不能为空！");
        }

        username = username.trim();

        try {
            RsaKeyPair keyPair = rsaKeyPairQueue.takeQueue(request);
            if (keyPair == null || StringUtils.isBlank(keyPair.getPrivateKey())) {
                logger.error("登录提交认证时，rsa公私钥队列相关操作异常，keyPair=" + keyPair);
                throw new AuthenticationServiceException("登录异常！");
            }
            String privateKey = keyPair.getPrivateKey();
            password = RSAEncrypt.decryptByPrivateKeyStr(privateKey, password);
        } catch (InterruptedException e) {
            logger.error("登录提交认证时，rsa公私钥队列相关操作异常", e);
            throw new AuthenticationServiceException("登录异常！");
        } catch (BizRuntimeException e) {
            logger.error("登录提交认证时，可能rsa解密异常", e);
            throw new AuthenticationServiceException("登录异常！");
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "用户名不能为空！");
        this.usernameParameter = usernameParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "密码不能为空！");
        this.passwordParameter = passwordParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getUsernameParameter() {
        return usernameParameter;
    }

    public final String getPasswordParameter() {
        return passwordParameter;
    }

    public void setRsaKeyPairQueue(RsaKeyPairQueue rsaKeyPairQueue) {
        this.rsaKeyPairQueue = rsaKeyPairQueue;
    }

}

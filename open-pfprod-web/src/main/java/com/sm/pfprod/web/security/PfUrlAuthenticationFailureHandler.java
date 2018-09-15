package com.sm.pfprod.web.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @ClassName: PfUrlAuthenticationFailureHandler
 * @Description: 认证失败时的请求处理
 * @Author yangtongbin
 * @Date 2017/9/1 21:41
 */
public class PfUrlAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(PfUrlAuthenticationFailureHandler.class);

    private String defaultFailureUrl;
    private boolean forwardToDestination = false;
    private boolean allowSessionCreation = true;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public PfUrlAuthenticationFailureHandler() {
    }

    public PfUrlAuthenticationFailureHandler(String defaultFailureUrl) {
        setDefaultFailureUrl(defaultFailureUrl);
    }

    /**
     * <p>Title: onAuthenticationFailure</p>
     * <p>Description: 认证失败的处理，如果defaultFailureUrl注入设置了，跳转到defaultFailureUrl页面，如果defaultFailureUrl未设置，将发送401错误信息</p>
     *
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(HttpServletRequest, HttpServletResponse, org.springframework.security.core.AuthenticationException)
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {
        if (defaultFailureUrl == null) {
            logger.debug("defaultFailureUrl未注入, 将发送401错误信息");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "认证失败: " + exception.getMessage());
        } else {
            saveException(request, exception);
            if (forwardToDestination) {
                logger.debug("认证失败，将跳转到" + defaultFailureUrl);
                request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
            } else {
                logger.debug("认证失败，将重定向到" + defaultFailureUrl);
                String errorMsg = "/";
                if (exception != null && StringUtils.isNotBlank(exception.getMessage())) {
                    errorMsg += Base64.encodeBase64URLSafeString(exception.getMessage().getBytes());
                }
                redirectStrategy.sendRedirect(request, response, defaultFailureUrl + errorMsg);
            }
        }
    }

    protected final void saveException(HttpServletRequest request,
                                       AuthenticationException exception) {
        if (forwardToDestination) {
            request.setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
        } else {
            HttpSession session = request.getSession(false);
            if (session != null || allowSessionCreation) {
                request.getSession().setAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, exception);
            }
        }
    }

    /**
     * 设置登录失败时要跳转的URL
     *
     * @param defaultFailureUrl 登录认证失败时要跳转的URL, "/loginFailed.jsp"
     */
    public void setDefaultFailureUrl(String defaultFailureUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl), "'" + defaultFailureUrl + "' is not a valid redirect URL");
        this.defaultFailureUrl = defaultFailureUrl;
    }

    protected boolean isUseForward() {
        return forwardToDestination;
    }

    public void setUseForward(boolean forwardToDestination) {
        this.forwardToDestination = forwardToDestination;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

    protected boolean isAllowSessionCreation() {
        return allowSessionCreation;
    }

    public void setAllowSessionCreation(boolean allowSessionCreation) {
        this.allowSessionCreation = allowSessionCreation;
    }

}

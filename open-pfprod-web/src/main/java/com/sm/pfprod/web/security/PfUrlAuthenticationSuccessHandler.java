package com.sm.pfprod.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: PfUrlAuthenticationSuccessHandler
 * @Description: 认证成功时的请求处理
 * @Author yangtongbin
 * @Date 2017/9/1 21:41
 */
public class PfUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    private static final Logger LOGGER = LoggerFactory.getLogger(PfUrlAuthenticationSuccessHandler.class);

    private String targetUrl;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public PfUrlAuthenticationSuccessHandler() {
    }

    public PfUrlAuthenticationSuccessHandler(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        LOGGER.debug("Redirecting to successRequest Url:{} ", targetUrl);
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    public void setTargetUrl(String targetUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(targetUrl), "'" + targetUrl + "' is not a valid redirect URL");
        this.targetUrl = targetUrl;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
}

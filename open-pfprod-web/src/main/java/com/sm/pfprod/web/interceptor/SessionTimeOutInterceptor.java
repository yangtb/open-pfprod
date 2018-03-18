package com.sm.pfprod.web.interceptor;

import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.pfprod.web.security.SecurityContext;
import com.sm.pfprod.web.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: SessionTimeOutInterceptor
 * @Description: session超时处理类
 * @Author yangtongbin
 * @Date 2018/3/16 17:49
 */
public class SessionTimeOutInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionTimeOutInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        User user = SecurityContext.currentUser();
        if (user == null || (user.getUserId() == null)) {
            LOGGER.info("session失效, url:{}", request.getRequestURI());
            try {
                // 1:判断是否是ajax请求
                if (request.getHeader("x-requested-with") != null
                        && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {
                    throw new BizRuntimeException("loginTimeout", "登陆超时，请重新登录");
                }
                RequestDispatcher rd = request.getRequestDispatcher("/loginTimeout");
                rd.forward(request, response);
            } catch (BizRuntimeException e) {
                throw new BizRuntimeException(e.getErrorCode(), e.getMessage());
            } catch (Exception e) {
                LOGGER.error("session超时处理异常：usId={}", user.getUserId());
            }
            return false;
        }
        return true;
    }
}

package com.sm.pfprod.web.interceptor;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.exception.BizRuntimeException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限过滤器
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getServletPath();
        //权限校验,判断是否包含权限
        Subject subject = SecurityUtils.getSubject();
        //UserRealm.doGetAuthorizationInfo，判断是否包含此url的响应权限
        boolean isPermitted = subject.isPermitted(url);
        if (!isPermitted) {
            throw new BizRuntimeException(ErrorCode.ERROR_SYS_160008, ErrorMessage.MESSAGE_SYS_160008);
        }
        return isPermitted;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

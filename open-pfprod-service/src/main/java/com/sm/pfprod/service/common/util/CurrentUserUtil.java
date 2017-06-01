package com.sm.pfprod.service.common.util;

import com.alibaba.fastjson.JSON;
import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.log.LoggerUtil;
import com.sm.pfprod.model.dto.common.User;
import com.sm.pfprod.model.vo.common.auth.UserAuthVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CurrentUserUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserUtil.class);

    /**
     * 设置当前用户信息到session中
     *
     * @param request
     * @param user
     */
    public static void setCurrentUser(HttpServletRequest request, UserAuthVo user) {
        /* 1-校验 */
        if (null == user) {
            LoggerUtil.error(LOGGER, "ready put session; but user is null");
            return;
        }
        /* 2-保存用户信息到Session */
        try {
            request.getSession().setAttribute(AuthConstant.CURRENT_USER, new UserAuthVo(user.getUserId(), user.getAccessToken()));
            LoggerUtil.info(LOGGER, "获取用户信息成功; sid:{0}, user:{1}", request.getSession().getId(), JSON.toJSONString(user));
        } catch (Exception e) {
            LoggerUtil.error(LOGGER, " 获取用户信息失败：{0}", e.getMessage());
        }
    }

    /**
     * 获取当前登录的用户
     *
     * @param request
     * @return
     */
    public static User currentUser(HttpServletRequest request) {
        User user = null;
        try {
            user = (User) request.getSession().getAttribute(AuthConstant.CURRENT_USER);
            if (null == user || null == user.getUserId()) {
                return new User();
            }
        } catch (Exception e) {
            LoggerUtil.info(LOGGER, "currentUser; key:{0} >> error: {1}", AuthConstant.CURRENT_USER, e.getMessage());
        }
        return user;
    }

    /**
     * 动态获取当前的用户Id
     */
    public static Long getCurrentUserId(HttpServletRequest request) {
        User user = new User();
        if (null == user || null == user.getUserId()) {
            throw new BizRuntimeException(ErrorCode.ERROR_NET_150001, "session_user为空，访问被拒绝");
        }
        return user.getUserId();
    }

}

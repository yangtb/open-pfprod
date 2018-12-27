package com.sm.pfprod.web.exception;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.exception.BizRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: GlobalExceptionResolver
 * @Description: 全局异常处理器
 * @Author yangtongbin
 * @Date 2017/9/14 11:30
 */
@ControllerAdvice
public class GlobalExceptionResolver {


    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultObject globalExceptionResolver(HttpServletRequest request, Exception e) {
        String requestUrl = request.getServletPath();
        if (e instanceof BizRuntimeException) {
            BizRuntimeException bizEx = (BizRuntimeException) e;
            return ResultObject.create(requestUrl, bizEx.getErrorCode(), bizEx.getMessage());
        } else if (e instanceof AccessDeniedException) {
            if (isAjaxReq(request)) {
                return ResultObject.create(requestUrl, ErrorCode.ERROR_NET_150001, "没有访问权限");
            }
            // 无权限异常抛到上层
            throw new AccessDeniedException(e.getMessage());
        } else {
            LOGGER.info("【GlobalExceptionResolver】服务器内部错误, url:{}, msg:{}", requestUrl, e.getMessage());
            return ResultObject.create(requestUrl, ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
        }
    }

    /**
     * 判断是否是ajax请求
     *
     * @param request
     * @return
     */
    private boolean isAjaxReq(HttpServletRequest request) {
        return request.getHeader("x-requested-with") != null
                && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"));
    }

}

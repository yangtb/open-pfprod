package com.sm.pfprod.web.exception;

import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.ResultObject;
import com.sm.open.care.core.exception.BizRuntimeException;
import com.sm.open.care.core.log.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionResolver.class);

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ResultObject GlobalExceptionResolver(HttpServletRequest request, Exception e){
		String requestUrl = request.getServletPath();
		LoggerUtil.error(LOGGER, "接口请求异常:{0}, e.getMessage():{1}", requestUrl, e.getMessage());
		if (e instanceof BizRuntimeException) {
			BizRuntimeException bizEx = (BizRuntimeException) e;
			return ResultObject.create(requestUrl, bizEx.getErrorCode(), bizEx.getMessage());
		} else {
			return ResultObject.create(requestUrl, ErrorCode.ERROR_SYS_160002, ErrorMessage.MESSAGE_SYS_160002);
		}
	}

}

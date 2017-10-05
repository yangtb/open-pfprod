package com.sm.pfprod.web.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: YtAccessDeniedHandler
 * @Description: 非法访问（认证异常）拒绝的处理
 * @author 王勇琳
 * @date 2017年6月30日 下午6:16:12
 */
public class PfAccessDeniedHandler implements AccessDeniedHandler {
	protected static final Log logger = LogFactory.getLog(PfAccessDeniedHandler.class);

	private String errorPage;

	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if (!response.isCommitted()) {
			if (errorPage != null) {
				request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
				dispatcher.forward(request, response);
			} else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
			}
		}
	}

	public void setErrorPage(String errorPage) {
		if ((errorPage != null) && !errorPage.startsWith("/")) {
			throw new IllegalArgumentException("错误页面地址必须以'/'开始！");
		}
		this.errorPage = errorPage;
	}
	
}

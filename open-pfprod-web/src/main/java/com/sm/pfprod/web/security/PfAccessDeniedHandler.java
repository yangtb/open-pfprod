package com.sm.pfprod.web.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: PfAccessDeniedHandler
 * @Description: 非法访问（认证异常）拒绝的处理
 * @Author yangtongbin
 * @Date 2017/10/12 21:39
 */
public class PfAccessDeniedHandler implements AccessDeniedHandler {

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

package com.sm.pfprod.web.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: PfExceptionTranslationFilter
 * @Description: 异常解析过滤器
 * @Author yangtongbin
 * @Date 2017/10/12 21:39
 */
public class PfExceptionTranslationFilter extends GenericFilterBean {

	private AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();
	private AuthenticationEntryPoint authenticationEntryPoint;
	private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
	private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

	private RequestCache requestCache = new HttpSessionRequestCache();

	public PfExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint) {
		this(authenticationEntryPoint, new HttpSessionRequestCache());
	}

	public PfExceptionTranslationFilter(AuthenticationEntryPoint authenticationEntryPoint, RequestCache requestCache) {
		Assert.notNull(authenticationEntryPoint, "AuthenticationEntryPoint注入不能为空！");
		Assert.notNull(requestCache, "RequestCache注入不能为空！");
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.requestCache = requestCache;
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(authenticationEntryPoint, "authenticationEntryPoint必须定义！");
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		try {
			chain.doFilter(request, response);
			logger.debug("异常解析过滤链执行正常。");
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			Throwable[] causeChain = throwableAnalyzer.determineCauseChain(ex);
			RuntimeException ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
			if (ase == null) {
				ase = (AccessDeniedException) throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
			}
			if (ase != null) {
				handleSpringSecurityException(request, response, chain, ase);
			} else {
				if (ex instanceof ServletException) {
					throw (ServletException) ex;
				} else if (ex instanceof RuntimeException) {
					throw (RuntimeException) ex;
				}
				throw new RuntimeException(ex);
			}
		}
	}

	public AuthenticationEntryPoint getAuthenticationEntryPoint() {
		return authenticationEntryPoint;
	}

	protected AuthenticationTrustResolver getAuthenticationTrustResolver() {
		return authenticationTrustResolver;
	}

	private void handleSpringSecurityException(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain, RuntimeException exception) throws IOException, ServletException {
		if (exception instanceof AuthenticationException) {
			logger.debug( "AuthenticationException发生了; 将执行entry-point-ref（AuthenticationEntryPoint）", exception);
			sendStartAuthentication(request, response, chain, (AuthenticationException) exception);
		} else if (exception instanceof AccessDeniedException) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authenticationTrustResolver.isAnonymous(authentication) || authenticationTrustResolver.isRememberMe(authentication)) {
				logger.debug("访问被拒绝 (当前用户" + (authenticationTrustResolver.isAnonymous(authentication) ? "是【匿名用户anonymousUser】" : "没有被完整认证") + "); 将执行entry-point-ref（AuthenticationEntryPoint）", exception);
				sendStartAuthentication( request, response, chain, new InsufficientAuthenticationException("访问当前资源必须经过完整的认证"));
			} else {
				logger.debug("访问被拒绝 (当前用户不是匿名用户); 将交给AccessDeniedHandler处理", exception);
				accessDeniedHandler.handle(request, response, (AccessDeniedException) exception);
			}
		}
	}

	protected void sendStartAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain,
			AuthenticationException reason) throws ServletException, IOException {
		SecurityContextHolder.getContext().setAuthentication(null);
		//保存当前此次请求到session中
		requestCache.saveRequest(request, response);
		logger.debug("将执行entry-point-ref（AuthenticationEntryPoint）。");
		authenticationEntryPoint.commence(request, response, reason);
	}

	public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
		Assert.notNull(accessDeniedHandler, "AccessDeniedHandler注入不能为空！");
		this.accessDeniedHandler = accessDeniedHandler;
	}

	public void setAuthenticationTrustResolver(AuthenticationTrustResolver authenticationTrustResolver) {
		Assert.notNull(authenticationTrustResolver, "AuthenticationTrustResolver注入不能为空！");
		this.authenticationTrustResolver = authenticationTrustResolver;
	}

	public void setThrowableAnalyzer(ThrowableAnalyzer throwableAnalyzer) {
		Assert.notNull(throwableAnalyzer, "ThrowableAnalyzer注入不能为空！");
		this.throwableAnalyzer = throwableAnalyzer;
	}

	private static final class DefaultThrowableAnalyzer extends ThrowableAnalyzer {
		protected void initExtractorMap() {
			super.initExtractorMap();
			registerExtractor(ServletException.class, new ThrowableCauseExtractor() {
				public Throwable extractCause(Throwable throwable) {
					ThrowableAnalyzer.verifyThrowableHierarchy(throwable, ServletException.class);
					return ((ServletException) throwable).getRootCause();
				}
			});
		}
	}

}

package com.sm.pfprod.web.security;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName: PfConcurrentSessionControlAuthenticationStrategy
 * @Description: 用户session的并发控制策略
 * @Author yangtongbin
 * @Date 2017/9/4 13:31
 */
public class PfConcurrentSessionControlAuthenticationStrategy implements MessageSourceAware, SessionAuthenticationStrategy {
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	private final SessionRegistry sessionRegistry;
	/** exceptionIfMaximumExceeded, 当超过最大session数时: true: 不允许新session, 保持旧session ，false: 销毁旧session, 新session生效 */
	private boolean exceptionIfMaximumExceeded = false;
	private int maximumSessions = 1;

	public PfConcurrentSessionControlAuthenticationStrategy(SessionRegistry sessionRegistry) {
		Assert.notNull(sessionRegistry, "SessionRegistry注入不能为空！");
		this.sessionRegistry = sessionRegistry;
	}

	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
		final List<SessionInformation> sessions = sessionRegistry.getAllSessions(authentication.getPrincipal(), false);
		int sessionCount = sessions.size();
		int allowedSessions = getMaximumSessionsForThisUser(authentication);
		if (sessionCount < allowedSessions) {
			// 用户session数量在允许范围之内
			return;
		}
		if (allowedSessions == -1) {
			return;
		}
		if (sessionCount == allowedSessions) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				// 仅允许已经存在的用户session通过
				for (SessionInformation si : sessions) {
					if (si.getSessionId().equals(session.getId())) {
						return;
					}
				}
			}
		}
		allowableSessionsExceeded(sessions, allowedSessions, sessionRegistry);
	}

	protected int getMaximumSessionsForThisUser(Authentication authentication) {
		return maximumSessions;
	}

	protected void allowableSessionsExceeded(List<SessionInformation> sessions,
			int allowableSessions, SessionRegistry registry) throws SessionAuthenticationException {
		if (exceptionIfMaximumExceeded || (sessions == null)) {
			throw new SessionAuthenticationException(messages.getMessage(
					"PfConcurrentSessionControlAuthenticationStrategy.exceededAllowed",
					new Object[] { Integer.valueOf(allowableSessions) }, "该用户的session达到上限，不允许再登录！"));
		}
		// 确定最少最近使用的session，将其置为无效
		SessionInformation leastRecentlyUsed = null;
		for (SessionInformation session : sessions) {
			if ((leastRecentlyUsed == null) || session.getLastRequest().before(leastRecentlyUsed.getLastRequest())) {
				leastRecentlyUsed = session;
			}
		}
		leastRecentlyUsed.expireNow();
	}

	public void setExceptionIfMaximumExceeded(boolean exceptionIfMaximumExceeded) {
		this.exceptionIfMaximumExceeded = exceptionIfMaximumExceeded;
	}

	/**
	 * 不能设置为0！设置-1表示不限制，设置其他正数表示最大允许session个数。
	 * @param maximumSessions
	 */
	public void setMaximumSessions(int maximumSessions) {
		Assert.isTrue(maximumSessions != 0, "不能设置为0！设置-1表示不限制，设置其他正数表示最大允许session个数。");
		this.maximumSessions = maximumSessions;
	}

	/**
	 * <p>Title: setMessageSource</p>
	 * <p>Description: 当用户超过了认证的最大数量时，用于报告错误反馈给用户</p>
	 * @param messageSource
	 * @see MessageSourceAware#setMessageSource(MessageSource)
	 */
	@Override
	public void setMessageSource(MessageSource messageSource) {
		Assert.notNull(messageSource, "MessageSource注入不能为空");
		this.messages = new MessageSourceAccessor(messageSource);
	}
	
}

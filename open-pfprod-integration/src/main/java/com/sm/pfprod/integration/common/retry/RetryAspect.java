package com.sm.pfprod.integration.common.retry;

import com.sm.open.care.core.log.LoggerUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RetryAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger("monitor");

	/** 尝试执行次数 */
	@Value(value = "${integration.retry.num}")
	private int defaultRetryTimes = 3;

	@Around("@annotation(retry)")
	public Object around(ProceedingJoinPoint point, Retryable retry) throws Throwable {
		int retryTimes = defaultRetryTimes;
		if (retry.retryTimes() >= 0) {
			retryTimes = retry.retryTimes();
		}
		for (int i = 0; i <= retryTimes; i++) {
			try {
				return point.proceed();
			} catch (Exception e) {
				LoggerUtil.error(e, LOGGER, "【Retry执行异常，第{0}次执行】", i);
			}
		}
		return null;
	}

}

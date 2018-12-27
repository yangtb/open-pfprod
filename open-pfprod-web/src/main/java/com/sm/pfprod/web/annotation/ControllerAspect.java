package com.sm.pfprod.web.annotation;

import com.alibaba.fastjson.JSON;
import com.sm.open.care.core.log.LogTrace;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ControllerAspect
 * @Description: 切面监控
 * @Author yangtongbin
 * @Date 2018/10/24
 */
@Component
@Aspect
public class ControllerAspect {

	private static final Logger MONITOR_LOG = LoggerFactory.getLogger("monitor");

	private static final String PREFIX = "monitor-";


	@Pointcut("within(@org.springframework.stereotype.Controller *)")
	public void cutController() {
	}

	@Around("cutController()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		String className = point.getTarget().getClass().getSimpleName();
		String methodName = point.getSignature().getName();
		long start = System.currentTimeMillis();
		Object result = point.proceed();
		long end = System.currentTimeMillis();
		LogTrace trace = LogTrace.createLogTrace(null, null, PREFIX + className + "-" + methodName,
				(end - start), null, "(" + JSON.toJSONString(result).length() + ")");
		MONITOR_LOG.info(trace.build());
		return result;
	}

}

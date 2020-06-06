package com.sm.pfprod.web.annotation;

import com.alibaba.dubbo.rpc.RpcException;
import com.sm.open.care.core.ErrorCode;
import com.sm.open.care.core.ErrorMessage;
import com.sm.open.care.core.exception.BizRuntimeException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ClientAspect
 * @Description: 调用rpc异常切面拦截
 * @Author yangtongbin
 * @Date 2018/9/17 10:40
 */
@Component
@Aspect
public class ClientAspect {

    @Pointcut("execution(* com.sm.pfprod.integration..*.*(..))")
    public void cutClient() {
    }

    @Around("cutClient()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result;
        try {
            result = point.proceed();
        } catch (RpcException throwable) {
            throw new BizRuntimeException(
                    ErrorCode.DEFAULT_RPC_INVOKE_EXCEPTION, ErrorMessage.DEFAULT_RPC_INVOKE_EXCEPTION_MSG);
        }
        return result;
    }

}

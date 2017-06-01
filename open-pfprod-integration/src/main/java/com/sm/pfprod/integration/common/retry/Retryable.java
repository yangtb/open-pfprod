package com.sm.pfprod.integration.common.retry;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Retryable {

	int retryTimes() default -1;

}

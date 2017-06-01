package com.sm.pfprod.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class TestBase {

	protected static ClassPathXmlApplicationContext context;

	@BeforeClass
	public static void beforeClass() {
		context = new ClassPathXmlApplicationContext("classpath:META-INF/spring/pfprod_service_server.xml");
	}

	@AfterClass
	public static void afterClass() {
		if (context != null) {
			context.close();
			context = null;
		}
	}

}

package com.softpoint.addressbook.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringBoot {
	private static AnnotationConfigApplicationContext context;

	public static void init() {
		try {
			context = new AnnotationConfigApplicationContext(AppConfig.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static AnnotationConfigApplicationContext getContext() {
		return context;
	}
}

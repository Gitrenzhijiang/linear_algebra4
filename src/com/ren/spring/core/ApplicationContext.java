package com.ren.spring.core;

public interface ApplicationContext {
	Object getBean(String id);
	void destory();
}

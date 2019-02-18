package com.ren.spring.core;

import com.ren.struts.core.Action;
import com.ren.struts.core.GetActionAndInteceptor;
import com.ren.struts.core.Inteceptor;

/**
 * 为了让struts能够用spring的action
 * @author 任志江
 *
 */
public class GetActionAndInteceptorImp implements GetActionAndInteceptor{
	private ApplicationContext applicationContext;
	public GetActionAndInteceptorImp() {
		//初始化srping容器
		applicationContext = new ClassPathXmlApplicationContext("spring.xml");
	}
	
	@Override
	public Action getAction(String actionName) {
		
		return (Action) applicationContext.getBean(actionName);
	}

	@Override
	public Inteceptor getInteceptor(String inteceptorName) {
		
		return (Inteceptor) applicationContext.getBean(inteceptorName);
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
}

package com.ren.struts.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.linear.action.UserAction;

public class ActionInvocation {
	
	
	public ActionInvocation(ActionContext actionContext) {
		this.actionContext = actionContext;
	}
	//执行action 的方法
	public String invoke() {
		if(iterator==null) {
			this.iterator = actionContext.getInteceptors().iterator();
		}
		String res = null;
		if(iterator.hasNext()) {
			Inteceptor inteceptor = iterator.next();
			res = inteceptor.inteceptor(this);
		}else {
			res = this.executeActionOnly(actionContext.getActionfig(),actionContext.getAction());
		}
		return res;
	}
	
	//执行action的方法
	private String executeActionOnly(ActionConfig actionConfig, Action action) {
		
		try {
			String methodName = actionConfig.getMethod();
			if(methodName != null && !"".equals(methodName)) {
				Class clazz = action.getClass();
				Method method = clazz.getDeclaredMethod(actionConfig.getMethod(), null);
				return (String) method.invoke(action, null);
			}else {
				return action.execute();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			iterator = null;
		}
		return null;
	}
	
	public ActionContext getActionContext() {
		return actionContext;
	}

	private ActionContext actionContext;
	private Iterator<Inteceptor> iterator;
	
}

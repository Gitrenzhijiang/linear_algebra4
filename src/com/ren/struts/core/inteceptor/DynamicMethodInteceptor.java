package com.ren.struts.core.inteceptor;

import com.ren.struts.core.ActionContext;
import com.ren.struts.core.ActionInvocation;
import com.ren.struts.core.Inteceptor;
/**
 * 动态方法调用
 * @author 任志江
 *
 */
public class DynamicMethodInteceptor implements Inteceptor{

	@Override
	public String inteceptor(ActionInvocation actionInvocation) {
		String res = null;
		String url = actionInvocation.getActionContext().getReq().getRequestURI();
		ActionContext acontext = actionInvocation.getActionContext();
		if(url.contains("!")) {
			String methodName = url.substring(url.indexOf("!") + 1);
			
			acontext.getActionfig().setMethod(methodName);
		}
		res = actionInvocation.invoke();
		return res;
	}
	
}

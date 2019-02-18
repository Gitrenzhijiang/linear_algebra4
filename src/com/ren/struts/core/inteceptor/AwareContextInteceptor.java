package com.ren.struts.core.inteceptor;

import com.ren.struts.core.Action;
import com.ren.struts.core.ActionContext;
import com.ren.struts.core.ActionInvocation;
import com.ren.struts.core.Inteceptor;
import com.ren.struts.core.RequestAware;
import com.ren.struts.core.ResponseAware;
import com.ren.struts.core.SessionAware;

public class AwareContextInteceptor implements Inteceptor {

	@Override
	public String inteceptor(ActionInvocation actionInvocation) {
		ActionContext actionContext = actionInvocation.getActionContext();
		Action action = actionContext.getAction();
		if(action instanceof SessionAware) {
			 ((SessionAware)action).sessionAware(actionContext.getReq().getSession());
		}
		if(action instanceof RequestAware) {
			 ((RequestAware)action).requestAware(actionContext.getReq());
		}
		if(action instanceof ResponseAware) {
			 ((ResponseAware)action).responseAware(actionContext.getRes());
		}
		String res = actionInvocation.invoke();
		return res;
	}

}

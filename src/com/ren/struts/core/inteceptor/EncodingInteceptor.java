package com.ren.struts.core.inteceptor;

import java.io.UnsupportedEncodingException;

import com.ren.struts.core.ActionContext;
import com.ren.struts.core.ActionInvocation;
import com.ren.struts.core.Inteceptor;

public class EncodingInteceptor implements Inteceptor {

	@Override
	public String inteceptor(ActionInvocation actionInvocation) {
		
		ActionContext ac = actionInvocation.getActionContext();
		try {
			ac.getReq().setCharacterEncoding("utf-8");
			ac.getRes().setContentType("text/html;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return actionInvocation.invoke();
	}

}

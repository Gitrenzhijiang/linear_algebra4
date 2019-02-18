package com.ren.struts.core.inteceptor;


import java.io.IOException;

import com.ren.struts.core.ActionContext;
import com.ren.struts.core.ActionInvocation;
import com.ren.struts.core.Inteceptor;

public class ChainActionInteceptor implements Inteceptor {

	@Override
	public String inteceptor(ActionInvocation actionInvocation) {
		String res = actionInvocation.invoke();
		ActionContext ac = actionInvocation.getActionContext();
		
		if(res == null)return null;
		
		if(res.contains(".action")) {
			try {
				ac.getRes().sendRedirect(ac.getRoot()+res);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			
				try {
					String resultValue = ac.getActionfig().getResultMap().get(res);
					if(resultValue != null) {
						ac.getReq().getRequestDispatcher(resultValue).forward(ac.getReq(), ac.getRes());
					}else if(!ac.getRes().isCommitted()){
						ac.getRes().sendError(404);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			
		}
		return res;
	}

}

package com.linear.web.inteceptor;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.linear.domain.Resource;
import com.linear.domain.User;
import com.linear.service.ResourceService;
import com.linear.service.RoleService;
import com.ren.struts.core.ActionContext;
import com.ren.struts.core.ActionInvocation;
import com.ren.struts.core.Inteceptor;

public class PowerInteceptor implements Inteceptor {
	
	private ResourceService resourceService;
	private RoleService roleService;
	private List<Resource> resList = null;
	
	private Resource getByActionName(List<Resource> list, String name, String method) {
		if(name == null || list == null)
			return null;
		for(Resource r:list) {
			if(r.getUrl().equals(name + ".action")) {
				if((r.getMethod() == null || "".equals(r.getMethod()) ) || r.getMethod().equals(method))
					return r;
			}
		}
		return null;
	}
	private boolean canexe(int role_id, Resource res) {
		if(res != null && res.getRole_id() == role_id)
			return true;
		return false;
	}
	@Override
	public String inteceptor(ActionInvocation actionInvocation) {
		ActionContext ac =actionInvocation.getActionContext();
		String actionName = ac.getActionfig().getName();
		String methodName = ac.getActionfig().getMethod();
		HttpSession session = ac.getReq().getSession();
		User my = (User)session.getAttribute("user");
		if(my == null) {//未登录
			if("user".equals(actionName)) {
				if("login".equals(methodName) || "toteachersignup".equals(methodName) || "tologin".equals(methodName) 
						||"toregist".equals(methodName) || "tearegist".equals(methodName)
						||"sturegist".equals(methodName) ||"mailcheck".equals(methodName)) {
					return actionInvocation.invoke();
				}
			}
			return "/user.action!tologin";
		}else {
			if(my.getRole_id() == roleService.getAdminRole().getPk_id())
				return actionInvocation.invoke();
			Resource res = getByActionName(this.resList, actionName, methodName);
			if(res == null || canexe(my.getRole_id(), res))
				return actionInvocation.invoke();
			else{//禁止
				return null;
			}
		}
		
	}
	public ResourceService getResourceService() {
		return resourceService;
	}
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
		resList = resourceService.getList(0, resourceService.getTotalNum());
	}
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}

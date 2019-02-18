package com.linear.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.linear.domain.PageBean;
import com.linear.domain.Resource;
import com.linear.domain.Role;
import com.linear.service.ResourceService;
import com.linear.service.RoleService;
import com.ren.struts.core.Action;
import com.ren.struts.core.RequestAware;

import cn.ren.utils.CommonUtils;

public class ResourceAction implements Action, RequestAware {
	/**
	 * 前往权限查看管理
	 * @return
	 */
	public String toresource() {
		headLoad.loadHead(req);
		String pageindex = req.getParameter("pageindex");
		PageBean pagebean = new PageBean(resourceService.getTotalNum(), pageindex);
		List<Resource> resList = resourceService.getList(pagebean.getLimit_start(),
				pagebean.getLimit_offset());
		req.setAttribute("resList", resList);
		req.setAttribute("pagebean", pagebean);
		return "resource";
	}
	/**
	 * 前往添加
	 * @return
	 */
	public String toadd() {
		headLoad.loadHead(req);
		List<Role> roleList = roleService.getList();
		req.setAttribute("roleList", roleList);
		return "resourceadd";
	}
	
	public String add() {
		Resource res = CommonUtils.toBean(req.getParameterMap(), Resource.class);
		try {
			resourceService.add(res);
		}catch(Exception e) {
			req.setAttribute("form_err", e.getMessage());
			return toadd();
		}
		return toresource();
	}
	public String delete() {
		String res_id = req.getParameter("res_id");
		try {
			resourceService.deleteById(Integer.parseInt(res_id));
		}catch(Exception e) {
			
		}
		return toresource();
	}
	
	private RoleService roleService;
	private ResourceService resourceService;
	private HeadLoad headLoad;
	@Override
	public void requestAware(HttpServletRequest req) {
		this.req = req;
	}
	private HttpServletRequest req;
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public ResourceService getResourceService() {
		return resourceService;
	}
	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}
	public HeadLoad getHeadLoad() {
		return headLoad;
	}
	public void setHeadLoad(HeadLoad headLoad) {
		this.headLoad = headLoad;
	}

}

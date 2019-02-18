package com.linear.service;

import java.util.List;

import com.linear.dao.ResourceDao;
import com.linear.dao.RoleDao;
import com.linear.domain.Resource;

public class ResourceService {
	
	public void add(Resource res) throws ServiceException {
		if(res == null) {
			throw new ServiceException("空的参数");
		}
		if(res.getUrl() == null || "".equals(res.getUrl())) {
			throw  new ServiceException("url为空");
		}
		if(res.getUrl().length()>50) {
			throw  new ServiceException("url长度超过50");
		}
		if(res.getMethod()!=null && res.getMethod().length()>20) {
			throw  new ServiceException("method长度超过20");
		}
		resourceDao.add(res);
	}
	
	private ResourceDao resourceDao;
	private RoleDao roleDao;
	public int getTotalNum() {
		return resourceDao.getTotalNum();
	}

	public List<Resource> getList(int start, int offset) {
		List<Resource> list =  resourceDao.getList(start, offset);
		for(Resource r:list) {
			r.setRole(roleDao.getById(r.getRole_id()));
		}
		return list;
	}

	public void deleteById(int pk_id) {
		resourceDao.deleteById(pk_id);
	}

	public ResourceDao getResourceDao() {
		return resourceDao;
	}

	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}



}

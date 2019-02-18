package com.linear.service;

import java.util.List;

import com.linear.dao.RoleDao;
import com.linear.domain.Role;

public class RoleService {
	private RoleDao roleDao;
	public Role getStudentRole() {
		return roleDao.getById(3);
	}
	public Role getTeacherRole() {
		return roleDao.getById(2);
	}
	public Role getAdminRole() {
		return roleDao.getById(1);
	}
	public Role getById(int role_id) {
		return roleDao.getById(role_id);
	}
	public List<Role> getList() {
		return roleDao.getList();
	}
	public RoleDao getRoleDao() {
		return roleDao;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	
}

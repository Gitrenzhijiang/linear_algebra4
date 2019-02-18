package com.linear.dao;

import java.util.List;

import com.linear.domain.Role;

public interface RoleDao {
	Role getById(int id);
	List<Role> getList();
}

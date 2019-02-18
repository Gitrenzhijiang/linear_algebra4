package com.linear.dao;

import java.util.List;

import com.linear.domain.User;

public interface UserDao {
	User getByUser_no(String no);
	List<User> getListByRole_id(int role_id);
	void deleteByPk_id(int id);
	void add(User user);
	User getById(int pk_id);
	void update(User u);
	List<User> getListNotIclude(int user_id);
	List<User> getTeaListByActivation(int activation, int start, int offset);
	int getTeaNumByActivation(int activation);
}

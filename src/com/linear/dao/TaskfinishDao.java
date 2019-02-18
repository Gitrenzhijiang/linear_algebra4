package com.linear.dao;

import java.util.List;

import com.linear.domain.Taskfinish;

public interface TaskfinishDao {
	List<Taskfinish> getListByUser_id(int user_id, int start, int num);
	List<Taskfinish> getListByUser_id(int user_id, int start);
	int getTaskfinishNumByUser_id(int num);
	void add(Taskfinish tf);
	Taskfinish getByUser_idAndTask_id(int user_id, int task_id);
	void update(Taskfinish taskfinish);
	int getTotalNumByTask_id(int task_id);
	List<Taskfinish> getListByTask_id(int task_id, int start, int offset);
	Taskfinish getById(int tfid);
	
}

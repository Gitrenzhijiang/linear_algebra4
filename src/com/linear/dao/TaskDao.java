package com.linear.dao;

import java.util.List;

import com.linear.domain.Task;

public interface TaskDao {
	List<Task> getListUnfinishByUser_id(int user_id, int start, int num);
	List<Task> getListFinishedByUser_id(int user_id, int start, int num);
	void add(Task task);
	Task getByCreatetime(String createtime);
	Task getById(int pk_id);
	int getTotalNumOfUnfinishByUser_id(int user_id);
	int getTotalNumOfFinishedByUser_id(int user_id);
	int getTotalNum();
	List<Task> getList(int start, int offset);
}

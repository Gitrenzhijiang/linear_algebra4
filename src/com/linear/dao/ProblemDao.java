package com.linear.dao;

import java.util.List;

import com.linear.domain.Problem;

public interface ProblemDao {
	List<Problem> getList(int start, int num);
	int getProblemNum();
	void add(Problem p);
	int getProblemNumOfLevel(int level);
	int getProblemNumOfIssolved(int issolved);
	List<Problem> getListOfLevel(int level, int limit_start, int limit_offset);
	List<Problem> getListOfIssolved(int issolved, int limit_start, int limit_offset);
	List<Problem> getListOfUser_id(int user_id, int limit_start, int limit_offset);
	Problem getById(int pid);
	int getProblemNumByUser_id(int user_id);
	void updatelevel(int problem_id, int level);
	void updateissolved(int problem_id, int i);
}

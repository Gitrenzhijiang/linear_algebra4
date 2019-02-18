package com.linear.dao;

import java.util.List;

import com.linear.domain.Answer;
import com.linear.domain.Discuss;

public interface AnswerDao {
	List<Answer> getList(int pid,int start, int end);
	void add(Answer answer);
	int getTotalAnswerNumOfProblemId(int pid);
	Answer getById(int a_id);
	void addDiscuss(Discuss discuss);
}

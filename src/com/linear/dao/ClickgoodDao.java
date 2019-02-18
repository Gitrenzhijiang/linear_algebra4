package com.linear.dao;

import java.util.List;

import com.linear.domain.Clickgood;

public interface ClickgoodDao {
	int getNumOfOkByAnswer_id(int answer_id);
	int getNumOfErrorByAnswer_id(int answer_id);
	void clickOfisgoodToAnswerByUser_id(int a_id,int user_id, int is_good);
	Clickgood getByUser_idAndAnswer_id(int user_id, int a_id);
	List<Clickgood> getByAnswer_id(int answer_id);
}

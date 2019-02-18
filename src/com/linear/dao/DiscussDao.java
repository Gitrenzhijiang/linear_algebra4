package com.linear.dao;

import java.util.List;

import com.linear.domain.Discuss;

public interface DiscussDao {
	List<Discuss> getListByAnswer_id(int answer_id);
}

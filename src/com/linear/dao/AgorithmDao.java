package com.linear.dao;

import java.util.List;

import com.linear.domain.Agorithm;

public interface AgorithmDao {
	void add(Agorithm ago);
	int getTotalNumByUser_id(int user_id);
	List<Agorithm> getListByUser_id(int user_id, int limit_start, int limit_offset);
	Agorithm getById(int ago_id);
	void deleteById(int ago_id) throws DaoException;
}

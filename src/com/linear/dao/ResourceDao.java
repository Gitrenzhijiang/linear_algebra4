package com.linear.dao;

import java.util.List;

import com.linear.domain.Resource;

public interface ResourceDao {
	void add(Resource res);
	void deleteById(int id);
	List<Resource> getList(int start, int num);
	int getTotalNum();
}

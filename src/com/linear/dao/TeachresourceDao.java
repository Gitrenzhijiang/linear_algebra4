package com.linear.dao;

import java.util.List;

import com.linear.domain.Teachresource;

public interface TeachresourceDao {
	void add(Teachresource t);
	void deleteById(int id);
	List<Teachresource> getListByUpload_id(int id, int start, int offset);
	int getTotalNum();
	List<Teachresource> getList(int start, int offset);
	Teachresource getById(int pk_id);
	int getTotalNumByUpload_id(int upload_id);
	
}

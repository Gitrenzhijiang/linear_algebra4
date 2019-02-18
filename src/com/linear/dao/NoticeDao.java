package com.linear.dao;

import java.util.List;

import com.linear.domain.Notice;

public interface NoticeDao {
	List<Notice> getList(int start, int num);
	List<Notice> getList(int start);
	int getNoticeNum();
	int getTotalNumByUser_id(int user_id);
	List<Notice> getListByUser_id(int user_id, int start, int offset);
	void add(Notice notice) throws DaoException;
	int getTotalSendNoticeByUser_id(int user_id);
	List<Notice> getSendListByUser_id(int user_id, int limit_start, int limit_offset);
	Notice getByNotice_id(int nid);
	void deleteNoticeById(int nid) throws DaoException;
	
}

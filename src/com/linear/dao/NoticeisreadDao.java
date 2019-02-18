package com.linear.dao;

import java.util.List;

import com.linear.domain.Notice;
import com.linear.domain.Noticeisread;

public interface NoticeisreadDao {
	List<Noticeisread> getListByUser_id(int user_id, int isread, int start, int num);
	List<Notice> getNoticeListByUser_id(int user_id, int isread, int start, int num);
	int getTotalNumOfUnreadByUser_id(int user_id);
	void add(Noticeisread ni);
	List<Noticeisread> getListByNotice_id(int notice_id);
	void deleteByNotice_id(int nid);
	void alreadyById(int nis_id);
	Noticeisread getNisByNidAndUser_id(int notice_id, int user_id);
	
}

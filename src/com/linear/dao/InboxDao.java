package com.linear.dao;

import java.util.List;

import com.linear.domain.Inbox;

public interface InboxDao {
	List<Inbox> getListByUserId(int id, int start, int num);
	List<Inbox> getListByUserId(int id, int isread, int start, int num);
	void add(Inbox inbox);
	int getTotalnumOfUnreadByRecv_id(int recv_id);
	int getTotalNumByRecv_id(int user_id);
	int getTotalNumOfReadedByRecv_id(int user_id);
	int getTotalNumBySendId(int send_id);
	List<Inbox> getListBySendId(int send_id, int limit_start, int limit_offset);
	Inbox getById(int inbox_id);
	void updateIsreadByInbox_id(int inbox_id, int isread);
}

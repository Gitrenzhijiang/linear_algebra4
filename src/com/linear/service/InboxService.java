package com.linear.service;

import java.util.Date;
import java.util.List;

import com.linear.dao.InboxDao;
import com.linear.dao.UserDao;
import com.linear.domain.Inbox;

public class InboxService {
	
	/**
	 * 获得该用户的未读邮件
	 * 邮件列表按照邮件的发送时间排序，即最后发送的在最前面
	 * 如果没有一份邮件，返回一个size=0的List
	 * @receive_id 邮件接受人ID
	 * @return
	 */
	public List<Inbox> getUnreadListByRecv_id(int receive_id,int start,int num ){
		List<Inbox> list = inboxDao.getListByUserId(receive_id,0, start, num);
		for(Inbox inbox : list) {
			inbox.setUserBySendId(userDao.getById(inbox.getSend_id()));
		}
		return list;
	}
	
	/**
	 * 某人已读邮件
	 * @param receive_id
	 * @param start
	 * @param num
	 * @return
	 */
	public List<Inbox> getReadedListByRecv_id(int receive_id,int start,int num ){
		List<Inbox> list = inboxDao.getListByUserId(receive_id,1, start, num);
		for(Inbox inbox : list) {
			inbox.setUserBySendId(userDao.getById(inbox.getSend_id()));
		}
		return list;
	}
	private InboxDao inboxDao;
	private UserDao userDao;
	
	public int getTotalnumOfUnreadByRecv_id(int pk_id) {
		return inboxDao.getTotalnumOfUnreadByRecv_id(pk_id);
	}
	/**
	 * 某人接受到的邮件总数
	 * @param user_id
	 * @return
	 */
	public int getTotalNumByRecv_id(int user_id) {
		return inboxDao.getTotalNumByRecv_id(user_id);
	}
	/**
	 * 某人所有的邮件
	 * @param user_id
	 * @param limit_start
	 * @param limit_offset
	 * @return
	 */
	public List<Inbox> getListByRecv_id(int user_id, int limit_start, int limit_offset) {
		List<Inbox> list = inboxDao.getListByUserId(user_id, limit_start, limit_offset);
		for(Inbox inbox : list) {
			inbox.setUserBySendId(userDao.getById(inbox.getSend_id()));
		}
		return list;
	}
	/**
	 * 某人已读邮件总数
	 * @param user_id
	 * @return
	 */
	public int getTotalNumOfReadedByRecv_id(int user_id) {
		return inboxDao.getTotalNumOfReadedByRecv_id(user_id);
	}
	/**
	 * 某人发送的邮件总数
	 * @param send_id
	 * @return
	 */
	public int getTotalNumBySendId(int send_id) {
		
		return inboxDao.getTotalNumBySendId(send_id);
	}
	/**
	 * 某人发送的邮件
	 * @param pk_id
	 * @param limit_start
	 * @param limit_offset
	 * @return
	 */
	public List<Inbox> getListBySendId(int send_id, int limit_start, int limit_offset) {
		List<Inbox> list = inboxDao.getListBySendId(send_id,limit_start,limit_offset);
		for(Inbox inbox : list) {
			inbox.setUserBySendId(userDao.getById(inbox.getSend_id()));
			inbox.setUserByReceiveId(userDao.getById(inbox.getReceive_id()));
		}
		return list;
	}

	public void add(Inbox inbox) throws ServiceException {
		//检验
		if(inbox == null) {
			throw new ServiceException("邮件参数为null");
		}
		if(userDao.getById(inbox.getReceive_id()) == null) {
			throw new ServiceException("邮件接收人不存在");
		}
		if(userDao.getById(inbox.getSend_id()) == null) {
			throw new ServiceException("邮件发送人不存在");
		}
		if(inbox.getReceive_id() == inbox.getSend_id()) {
			throw new ServiceException("邮件接受人不能是自己");
		}
		if(inbox.getSubject() == null || "".equals(inbox.getSubject())) {
			throw new ServiceException("邮件主题为空");
		}
		if(inbox.getSubject().length()>30) {
			throw new ServiceException("邮件主题长度超过30");
		}
		if(inbox.getMessage() == null || "".equals(inbox.getMessage())) {
			throw new ServiceException("邮件内容为空");
		}
		if(inbox.getMessage().length()>300) {
			throw new ServiceException("邮件内容长度超过300");
		}
		if(inbox.getSendtime() == null) {
			inbox.setSendtime(new Date());
		}
		if(inbox.getIsread() != 0) {
			inbox.setIsread(0);
		}
		inboxDao.add(inbox);
		
	}
	/**
	 * 设置这个inbox为已读
	 * @param inbox_id
	 */
	public void setAReadByInbox_id(int inbox_id) {
		//加载这个inbox
		Inbox inbox = inboxDao.getById(inbox_id);
		if(inbox != null) {
			inboxDao.updateIsreadByInbox_id(inbox_id, 1);
		}
	}

	public void deleteByInbox_Id(int inbox_id) {
		//加载这个inbox
		Inbox inbox = inboxDao.getById(inbox_id);
		if(inbox != null) {
			inboxDao.updateIsreadByInbox_id(inbox_id, 11);
		}
	}

	public InboxDao getInboxDao() {
		return inboxDao;
	}

	public void setInboxDao(InboxDao inboxDao) {
		this.inboxDao = inboxDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


}




package com.linear.service;

import java.util.Date;
import java.util.List;

import com.linear.dao.DaoException;
import com.linear.dao.NoticeDao;
import com.linear.dao.NoticeisreadDao;
import com.linear.dao.UserDao;
import com.linear.domain.Notice;
import com.linear.domain.Noticeisread;

public class NoticeService {
	/**
	 * 获得该用户的未读通知
	 * @param user_id
	 * @return
	 */
	public List<Notice> getListOfUnread(int user_id, int start, int num){
		List<Notice> list = noticeisreadDao.getNoticeListByUser_id(user_id,0,start,num);
		for(Notice n:list) {
			n.setUser(userDao.getById(n.getCreateuser_id()));
			//给每一个notice设置noticeisread
			n.setNoticeisreads(noticeisreadDao.getListByNotice_id(n.getPk_id()));
		}
		return list;
	}
	
	private UserDao userDao;
	private NoticeDao noticeDao;
	private NoticeisreadDao noticeisreadDao;
	public int getTotalNumOfUnreadByUser_id(int user_id) {
		return noticeisreadDao.getTotalNumOfUnreadByUser_id(user_id);
	}
	/**
	 * 获取用户的通知数量,即某人总共收到了多少通知
	 * @param user_id
	 * @return
	 */
	public int getTotalNumByUser_id(int user_id) {
		
		return noticeDao.getTotalNumByUser_id(user_id);
	}
	/**
	 * 某人所有接受到的通知
	 * @param pk_id
	 * @return
	 */
	public List<Notice> getListByUser_id(int user_id, int start, int offset) {
		List<Notice> list = noticeDao.getListByUser_id(user_id, start, offset);
		for(Notice n:list) {
			n.setUser(userDao.getById(n.getCreateuser_id()));
			n.setNoticeisreads(noticeisreadDao.getListByNotice_id(n.getPk_id()));
		}
		return list;
	}
	public void add(Notice notice) throws ServiceException {
		//检查
		if(notice==null) {
			throw new ServiceException("空的notice");
		}
		if(notice.getNtext() == null || "".equals(notice.getNtext())) {
			throw new ServiceException("通知内容为空");
		}
		if(notice.getNtext().length()>300) {
			throw new ServiceException("通知内容超过300");
		}
		if(notice.getNtitle()==null || "".equals(notice.getNtitle())) {
			throw new ServiceException("通知标题为空");
		}
		if(notice.getNtitle().length()>30) {
			throw new ServiceException("通知标题超过30");
		}
		if(userDao.getById(notice.getCreateuser_id()) == null) {
			throw new ServiceException("用户为空");
		}
		if(notice.getCreatetime() == null) {
			notice.setCreatetime(new Date());
		}
		
		try {
			noticeDao.add(notice);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}
	/**
	 * 返回该用户发布通知总数
	 * @param user_id
	 * @return
	 */
	public int getTotalSendNoticeByUser_id(int user_id) {
		return noticeDao.getTotalSendNoticeByUser_id(user_id);
	}
	/**
	 * 返回该用户所有发布的通知
	 * @param user_id
	 * @param limit_start
	 * @param limit_offset
	 * @return
	 */
	public List<Notice> getSendListByUser_id(int user_id, int limit_start, int limit_offset) {
		List<Notice> list = null;
		list = noticeDao.getSendListByUser_id(user_id, limit_start, limit_offset);
		for(Notice n:list) {
			n.setUser(userDao.getById(n.getCreateuser_id()));
		}
		return list;
	}
	public void deleteByNotice_id(int nid) throws ServiceException {
		Notice n = getByNotice_id(nid);
		if(n == null)
			throw new ServiceException("这个通知不存在");
		try {
			noticeDao.deleteNoticeById(nid);
		} catch (DaoException e) {
			throw new ServiceException("删除通知失败");
		}
	}
	public Notice getByNotice_id(int nid) {
		
		return noticeDao.getByNotice_id(nid);
	}
	public void alreadyByNis_id(int nis_id) {
		noticeisreadDao.alreadyById(nis_id);
	}
	public Noticeisread getNisByNidAndUser_id(int notice_id, int user_id) {
		return noticeisreadDao.getNisByNidAndUser_id(notice_id,user_id);
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public NoticeDao getNoticeDao() {
		return noticeDao;
	}
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}
	public NoticeisreadDao getNoticeisreadDao() {
		return noticeisreadDao;
	}
	public void setNoticeisreadDao(NoticeisreadDao noticeisreadDao) {
		this.noticeisreadDao = noticeisreadDao;
	}

}

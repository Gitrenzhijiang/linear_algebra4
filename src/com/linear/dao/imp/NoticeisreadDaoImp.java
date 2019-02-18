package com.linear.dao.imp;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.linear.dao.NoticeisreadDao;
import com.linear.domain.Notice;
import com.linear.domain.Noticeisread;

import cn.ren.jdbc.TxQueryRunner;

public class NoticeisreadDaoImp implements NoticeisreadDao{
	/**
	 * 得到该用户的通知读取情况
	 * @param user_id
	 * @return
	 */
	public List<Noticeisread> getListByUser_id(int user_id, int isread, int start, int num) {
		List<Noticeisread> list = null;
		String sql = "select * from noticeisread where user_id = ? and isread = ? limit ?,?";
		try {
			list = qr.query(sql, new BeanListHandler<Noticeisread>(Noticeisread.class),
					user_id,isread,start,num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Notice> getNoticeListByUser_id(int user_id, int isread, int start, int num) {
		List<Notice> list = null;
		String sql = "select notice.* from noticeisread,notice where notice.pk_id = noticeisread.notice_id and user_id = ? and isread = ? limit ?,?";
		try {
			list = qr.query(sql, new BeanListHandler<Notice>(Notice.class),
					user_id,isread,start,num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	private QueryRunner qr = new TxQueryRunner();
	/**
	 * 返回该用户未读通知个数
	 * @param user_id
	 * @return
	 */
	public int getTotalNumOfUnreadByUser_id(int user_id) {
		String sql = "select count(*) from noticeisread where user_id = ? and isread = 0";
		int res = 0;
		Number number = 0;
		try {
			number = (Number) qr.query(sql, new ScalarHandler(), user_id);
			res = number.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	public void add(Noticeisread ni) {
		String sql  = "insert into noticeisread (notice_id,user_id,isread)"
				+ " values (?,?,?)";
		try {
			qr.update(sql, ni.getNotice_id(),ni.getUser_id(),ni.getIsread());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<Noticeisread> getListByNotice_id(int notice_id) {
		String sql  = "select * from noticeisread where notice_id = ?";
		List<Noticeisread> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Noticeisread>(Noticeisread.class), notice_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public void deleteByNotice_id(int nid) {
		String sql = "delete from noticeisread where notice_id = ?";
		try {
			qr.update(sql, nid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void alreadyById(int nis_id) {
		String sql = "update noticeisread set isread = 1 where pk_id = ?";
		try {
			qr.update(sql, nis_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Noticeisread getNisByNidAndUser_id(int notice_id, int user_id) {
		Noticeisread nis = null;
		String sql = "select * from noticeisread where notice_id = ? and user_id = ?";
		try {
			nis = qr.query(sql, new BeanHandler<Noticeisread>(Noticeisread.class), notice_id,user_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nis;
	}
	
}

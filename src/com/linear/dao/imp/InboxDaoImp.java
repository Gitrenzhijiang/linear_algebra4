package com.linear.dao.imp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.linear.dao.InboxDao;
import com.linear.domain.Inbox;

import cn.ren.jdbc.TxQueryRunner;

public class InboxDaoImp implements InboxDao{
	/**
	 * 按照sendtime 降序 获得receive_id 的邮件
	 * 从第start开始，取num个(start从0开始)
	 * @param id receive_id
	 * @return
	 */
	public List<Inbox> getListByUserId(int id, int start, int num) {
		String sql = "select * from inbox where receive_id = ? and isread != 11 order by sendtime desc limit ?,?";
		List<Inbox> list = null;
		try {
			 list = qr.query(sql, new BeanListHandler<Inbox>(Inbox.class), id, start,num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 按照sendtime 降序 获得receive_id 的邮件
	 * 
	 * 从第start开始，取num个(start从0开始)
	 * @param id 接收人ID
	 * @param isread 邮件是否已读
	 * @return
	 */
	public List<Inbox> getListByUserId(int id, int isread, int start, int num) {
		String sql = "select * from inbox where receive_id = ? and isread = ? order by sendtime desc limit ?,?";
		List<Inbox> list = null;
		try {
			 list = qr.query(sql, new BeanListHandler<Inbox>(Inbox.class), id, isread, start,num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void add(Inbox inbox) {
		String sql = "insert into inbox (send_id,receive_id,subject,message,isread,sendtime)"
				+ " values (?,?,?,?,?,?)";
		try {
			qr.update(sql, inbox.getSend_id(),inbox.getReceive_id(),inbox.getSubject(),inbox.getMessage(),
				inbox.getIsread(),new Timestamp(inbox.getSendtime().getTime()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private QueryRunner qr = new TxQueryRunner();
	
	public int getTotalnumOfUnreadByRecv_id(int recv_id) {
		String sql = "select count(*) from inbox where receive_id = ? and isread = 0";
		int res = 0;
		Number number = 0;
		try {
			number = (Number) qr.query(sql, new ScalarHandler(), recv_id);
			res = number.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public int getTotalNumByRecv_id(int user_id) {
		String sql = "select count(*) from inbox where receive_id = ? and isread != 11";
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

	public int getTotalNumOfReadedByRecv_id(int user_id) {
		String sql = "select count(*) from inbox where receive_id = ? and isread = 1";
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

	public int getTotalNumBySendId(int send_id) {
		String sql = "select count(*) from inbox where send_id = ? ";
		int res = 0;
		Number number = 0;
		try {
			number = (Number) qr.query(sql, new ScalarHandler(), send_id);
			res = number.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public List<Inbox> getListBySendId(int send_id, int limit_start, int limit_offset) {
		String sql = "select * from inbox where send_id = ? order by sendtime desc limit ?,?";
		List<Inbox> list = null;
		try {
			 list = qr.query(sql, new BeanListHandler<Inbox>(Inbox.class), send_id, limit_start,limit_offset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Inbox getById(int inbox_id) {
		String sql = "select * from inbox where pk_id = ?";
		Inbox inbox = null;
		try {
			inbox = qr.query(sql, new BeanHandler<Inbox>(Inbox.class), inbox_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return inbox;
	}

	public void updateIsreadByInbox_id(int inbox_id, int isread) {
		String sql = "update inbox set isread = ? where pk_id = ?";
		try {
			qr.update(sql, isread, inbox_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

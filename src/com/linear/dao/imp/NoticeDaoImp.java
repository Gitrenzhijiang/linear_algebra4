package com.linear.dao.imp;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.linear.dao.DaoException;
import com.linear.dao.NoticeDao;
import com.linear.dao.NoticeisreadDao;
import com.linear.dao.UserDao;
import com.linear.domain.Notice;
import com.linear.domain.Noticeisread;
import com.linear.domain.User;
import com.linear.utils.DateFormat;

import cn.ren.jdbc.JdbcUtils;
import cn.ren.jdbc.TxQueryRunner;

public class NoticeDaoImp implements NoticeDao{
	/**
	 * 获得通知
	 * 通知列表按照时间降序排序
	 * @param start
	 * @param num
	 * @return
	 */
	public List<Notice> getList(int start, int num) {
		String sql = "select * from notice  order by createtime desc limit ?,?";
		List<Notice> list = null;
		try {
			 list = qr.query(sql, new BeanListHandler<Notice>(Notice.class), start,num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 得到从start开始的所有的Notice
	 * 通知列表按照时间降序排序
	 * @param start
	 * @return
	 */
	public List<Notice> getList(int start) {
		return getList(start, getNoticeNum() - start);
	}
	/**
	 * 获得Notice的数量
	 * @return
	 */
	public int getNoticeNum() {
		String sql = "select count(*) from notice";
		int num = 0;
		try {
			 Number number = (Number) qr.query(sql, new ScalarHandler());
			 num = number.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	private QueryRunner qr = new TxQueryRunner();
	/**
	 * 获取某人通知的接受数量
	 * @param user_id
	 * @return
	 */
	public int getTotalNumByUser_id(int user_id) {
		String sql = "select count(*) from notice where createuser_id != ?";
		int num = 0;
		try {
			 Number number = (Number) qr.query(sql, new ScalarHandler(), user_id);
			 num = number.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	public List<Notice> getListByUser_id(int user_id, int start, int offset) {
		String sql = "select * from notice where createuser_id != ? order by createtime desc limit ?,?";
		List<Notice> list = null;
		try {
			 list = qr.query(sql, new BeanListHandler<Notice>(Notice.class),user_id, start, offset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public void add(Notice notice) throws DaoException {
		String sql = "insert into notice (ntitle,ntext,createtime,createuser_id)"
				+ " values (?,?,?,?)";
		try {
			JdbcUtils.beginTransaction();
			qr.update(sql, notice.getNtitle(),notice.getNtext(),
					DateFormat.toString(notice.getCreatetime()),notice.getCreateuser_id());
			Notice dbnotice = getByCreatetime(notice.getCreatetime());
			List<User> list = userDao.getListNotIclude(notice.getCreateuser_id());
			for(User u:list) {
				Noticeisread ni = new Noticeisread();
				ni.setIsread(0);
				ni.setUser_id(u.getPk_id());
				ni.setNotice_id(dbnotice.getPk_id());
				nisDao.add(ni);
			}
			JdbcUtils.commitTransaction();
		}catch(Exception e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DaoException("添加通知失败");
		}
	}
	
	private Notice getByCreatetime(Date createtime) {
		String sql  = "select * from notice where createtime = ?";
		Notice n = null;
		try {
			n = qr.query(sql, new BeanHandler<Notice>(Notice.class), DateFormat.toString(createtime));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n;
	}
	private NoticeisreadDao nisDao;
	private UserDao userDao;
	
	public int getTotalSendNoticeByUser_id(int user_id) {
		String sql = "select count(*) from notice where createuser_id = ?";
		int num = 0;
		try {
			 Number number = (Number) qr.query(sql, new ScalarHandler(), user_id);
			 num = number.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	public List<Notice> getSendListByUser_id(int user_id, int limit_start, int limit_offset) {
		String sql = "select * from notice where createuser_id = ? order by createtime desc limit ?,?";
		List<Notice> list = null;
		try {
			 list = qr.query(sql, new BeanListHandler<Notice>(Notice.class),user_id, limit_start, limit_offset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public Notice getByNotice_id(int nid) {
		String sql = "select * from notice where pk_id = ?";
		Notice  n = null;
		try {
			n = qr.query(sql, new BeanHandler<Notice>(Notice.class), nid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n;
	}
	public void deleteNoticeById(int nid) throws DaoException {
		String sql = "delete from notice where pk_id = ?";
		try {
			JdbcUtils.beginTransaction();
			nisDao.deleteByNotice_id(nid);
			qr.update(sql, nid);
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DaoException("删除失败");
		}
	}
	public NoticeisreadDao getNisDao() {
		return nisDao;
	}
	public void setNisDao(NoticeisreadDao nisDao) {
		this.nisDao = nisDao;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}

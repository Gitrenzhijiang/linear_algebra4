package com.linear.dao.imp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.linear.dao.TaskfinishDao;
import com.linear.dao.UserDao;
import com.linear.domain.Taskfinish;

import cn.ren.jdbc.TxQueryRunner;

public class TaskfinishDaoImp implements TaskfinishDao{
	private UserDao userDao;
	/**
	 * 返回该用户的任务完成情况
	 * @param user_id
	 * @param start
	 * @param num
	 * @return
	 */
	public List<Taskfinish> getListByUser_id(int user_id, int start, int num) {
		List<Taskfinish> list = null;
		String sql = "select * from taskfinish where user_id = ? limit ?,?";
		try {
			list = qr.query(sql, new BeanListHandler<Taskfinish>(Taskfinish.class), user_id, start,num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Taskfinish> getListByUser_id(int user_id, int start) {
		return this.getListByUser_id(user_id, start, getTaskfinishNumByUser_id(user_id) - start);
	} 
	/**
	 * 返回该用户任务完成情况的数量
	 * @param num
	 * @return
	 */
	public int getTaskfinishNumByUser_id(int num) {
		int res = 0;
		String sql = "select count(*) from taskfinish";
		Number number = 0;
		try {
			number = (Number) qr.query(sql, new ScalarHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		res = number.intValue();
		return res;
	}
	
	private QueryRunner qr = new TxQueryRunner();

	public void add(Taskfinish tf) {
		String sql = "insert into taskfinish (task_id,status,user_id,finishtime)"
				+ "values (?,?,?,?)";
		try {
			qr.update(sql, tf.getTask_id(),tf.getStatus(),tf.getUser_id(),new Timestamp(tf.getFinishtime().getTime()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Taskfinish getByUser_idAndTask_id(int user_id, int task_id) {
		String sql = "select * from taskfinish where user_id = ? and task_id = ?";
		Taskfinish tf = null;
		try {
			tf = qr.query(sql, new BeanHandler<Taskfinish>(Taskfinish.class), user_id,task_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tf;
	}
	public void update(Taskfinish taskfinish) {
		String sql = "update taskfinish set status = ?,res = ?,finishtime=?,filepath = ?,remarkuser_id = ?,remark = ? where pk_id = ?";
		try {
			String ruid = taskfinish.getRemarkuser_id()+"";
			if(userDao.getById(taskfinish.getRemarkuser_id()) == null)
				ruid = null;
			qr.update(sql, new Object[] {taskfinish.getStatus(),
					taskfinish.getRes(),new Timestamp(taskfinish.getFinishtime().getTime()),
					taskfinish.getFilepath(),ruid,taskfinish.getRemark(),
					taskfinish.getPk_id()});
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int getTotalNumByTask_id(int task_id) {
		String sql = "select count(*) from taskfinish where task_id = ?";
		int res = 0;
		Number number = 0;
		try {
			number = (Number) qr.query(sql, new ScalarHandler(), task_id);
			res = number.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	public List<Taskfinish> getListByTask_id(int task_id, int start, int offset) {
		List<Taskfinish> list = null;
		String sql = "select * from taskfinish where task_id = ? limit ?,?";
		try {
			list = qr.query(sql, new BeanListHandler<Taskfinish>(Taskfinish.class), task_id, start,
					offset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public Taskfinish getById(int tfid) {
		String sql = "select * from taskfinish where pk_id = ?";
		Taskfinish tf = null;
		try {
			tf = qr.query(sql, new BeanHandler<Taskfinish>(Taskfinish.class), tfid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tf;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}

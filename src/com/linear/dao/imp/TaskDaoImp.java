package com.linear.dao.imp;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.linear.dao.TaskDao;
import com.linear.dao.TaskfinishDao;
import com.linear.dao.UserDao;
import com.linear.domain.Task;
import com.linear.domain.Taskfinish;
import com.linear.domain.User;
import com.linear.utils.DateFormat;

import cn.ren.jdbc.JdbcUtils;
import cn.ren.jdbc.TxQueryRunner;

public class TaskDaoImp implements TaskDao{
	
	
	/**
	 * 返回该用户未完成的任务
	 * @param user_id
	 * @param start
	 * @param num
	 * @return
	 */
	public List<Task> getListUnfinishByUser_id(int user_id, int start, int num) {
		String sql = "select task.* from task,taskfinish where task.pk_id = taskfinish.task_id and taskfinish.user_id = ? and taskfinish.status = 0 order by createtime desc limit ?,?";
		List<Task> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Task>(Task.class),user_id, start,num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 该用户已经完成的任务
	 * @param user_id
	 * @param start
	 * @param num
	 * @return
	 */
	public List<Task> getListFinishedByUser_id(int user_id, int start, int num) {
		String sql = "select task.* from task,taskfinish where task.pk_id = taskfinish.task_id and taskfinish.user_id = ? and taskfinish.status = 1 order by createtime desc limit ?,?";
		List<Task> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Task>(Task.class),user_id, start,num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 添加任务
	 * @param task
	 */
	public void add(Task task) {
		String sql = "insert into task (teacher_id,name,descript,filepath,createtime) values (?,?,?,?,?)";
		
		try {
			JdbcUtils.beginTransaction();
			qr.update(sql, task.getTeacher_id(),task.getName(),task.getDescript(),task.getFilepath(),DateFormat.toString(task.getCreatetime()));
			Task _task = getByCreatetime(DateFormat.toString(task.getCreatetime()));
			List<User> list = userDao.getListByRole_id(3);
			for(User u : list) {
				Taskfinish tf = new Taskfinish();
				tf.setTask_id(_task.getPk_id());
				tf.setStatus(0);
				tf.setUser_id(u.getPk_id());
				tf.setFinishtime(_task.getCreatetime());
				tfDao.add(tf);
			}
			JdbcUtils.commitTransaction();
		} catch (SQLException e) {
			try {
				JdbcUtils.rollbackTransaction();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	
	public Task getByCreatetime(String createtime) {
		
		String sql = "select * from task where createtime = ?";
		Task t = null;
		try {
			t = qr.query(sql, new BeanHandler<Task>(Task.class), createtime);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}
	public Task getById(int pk_id) {
		String sql = "select * from task where pk_id = ?";
		Task t = null;
		try {
			t = qr.query(sql, new BeanHandler<Task>(Task.class), pk_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return t;
	}

	private QueryRunner qr = new TxQueryRunner();

	private UserDao userDao;
	
	private TaskfinishDao tfDao;
	
	
	public int getTotalNumOfUnfinishByUser_id(int user_id) {
		String sql = "select count(*) from taskfinish where user_id = ? and status = 0";
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
	public int getTotalNumOfFinishedByUser_id(int user_id) {
		String sql = "select count(*) from taskfinish where user_id = ? and status = 1";
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
	/**
	 * 任务总数
	 */
	public int getTotalNum() {
		String sql = "select count(*) from task";
		int res = 0;
		Number number = 0;
		try {
			number = (Number) qr.query(sql, new ScalarHandler());
			res = number.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	public List<Task> getList(int start, int offset) {
		String sql = "select * from task order by createtime desc limit ?,?";
		List<Task> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Task>(Task.class), start, offset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public TaskfinishDao getTfDao() {
		return tfDao;
	}
	public void setTfDao(TaskfinishDao tfDao) {
		this.tfDao = tfDao;
	}
	
}

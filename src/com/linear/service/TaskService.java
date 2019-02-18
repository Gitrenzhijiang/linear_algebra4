package com.linear.service;

import java.util.Date;
import java.util.List;

import com.linear.dao.TaskDao;
import com.linear.dao.TaskfinishDao;
import com.linear.dao.UserDao;
import com.linear.domain.Task;
import com.linear.domain.Taskfinish;

public class TaskService {
	/**
	 * 返回该用户未完成的任务
	 * @param user_id
	 * @param start
	 * @param num
	 * @return
	 */
	public List<Task> getListUnfinishByUser_id(int user_id,int start, int num){
		List<Task> list = taskDao.getListUnfinishByUser_id(user_id, start, num);
		for(Task t:list) {
			t.setUser(userDao.getById(t.getTeacher_id()));
		}
		return list;
	}
	/**
	 * 添加任务
	 * @param task
	 * @throws ServiceException 
	 */
	public void add(Task task) throws ServiceException {
		//校验
		if(task == null) {
			throw new ServiceException("空的参数");
		}
		if(task.getName() == null || "".equals(task.getName())) {
			throw new ServiceException("任务名称为空");
		}
		if(task.getName().length() > 30) {
			throw new ServiceException("任务名称长度超过30");
		}
		if(task.getDescript() == null || "".equals(task.getDescript())) {
			throw new ServiceException("任务描述为空");
		}
		if(task.getDescript().length() > 300) {
			throw new ServiceException("任务描述长度超过300");
		}
		if(task.getFilepath() != null && task.getFilepath().length()>120) {
			throw new ServiceException("任务附件名称超长");
		}
		if(userDao.getById(task.getTeacher_id()) == null) {
			throw new ServiceException("无效的任务发布人");
		}
		if(task.getCreatetime() == null) {
			task.setCreatetime(new Date());
		}
		//添加任务
		taskDao.add(task);
	}
	
	private TaskDao taskDao;
	private UserDao userDao;
	public int getTotalNumOfUnfinishByUser_id(int user_id) {
		return taskDao.getTotalNumOfUnfinishByUser_id(user_id);
	}
	public int getTotalNumOfFinishedByUser_id(int user_id) {
		
		return taskDao.getTotalNumOfFinishedByUser_id(user_id);
	}
	/**
	 * 该用户已经完成的任务
	 * @param user_id
	 * @param start
	 * @param offset
	 * @return
	 */
	public List<Task> getListFinishedByUser_id(int user_id, int start, int offset) {
		
		List<Task> list = taskDao.getListFinishedByUser_id(user_id, start, offset);
		for(Task t:list) {
			t.setUser(userDao.getById(t.getTeacher_id()));
		}
		return list;
	}
	public Task getById(int task_id) {
		Task task = taskDao.getById(task_id);
		if(task != null)
			task.setUser(userDao.getById(task.getTeacher_id()));
		return task;
	}
	public Taskfinish getTaskfinishByUser_idAndTask_id(int user_id, int task_id) {
		
		Taskfinish tf = taskfinishDao.getByUser_idAndTask_id(user_id,task_id);
		tf.setTask(taskDao.getById(tf.getTask_id()));
		tf.setUserByRemarkuserId(userDao.getById(tf.getRemarkuser_id()));
		return tf;
	}
	private TaskfinishDao taskfinishDao;
	public void submitTask(Taskfinish taskfinish) throws ServiceException {
		if(taskfinish == null) {
			throw new ServiceException("参数为空");
		}
		if(taskDao.getById(taskfinish.getTask_id()) == null) {
			throw new ServiceException("无效的任务");
		}
		if(userDao.getById(taskfinish.getUser_id()) == null) {
			throw new ServiceException("无效的任务提交人");
		}
		if(taskfinish.getRes() == null || "".equals(taskfinish.getRes())) {
			throw new ServiceException("作业提交内容为空");
		}
		if(taskfinish.getRes().length()>300) {
			throw new ServiceException("作业提交内容长度超过300");
		}
		if(taskfinish.getFilepath() != null && taskfinish.getFilepath().length()>120) {
			throw new ServiceException("作业提交文件名长度过长");
		}
		if(taskfinish.getStatus() == 0) {
			taskfinish.setStatus(1);
		}
		if(taskfinish.getFinishtime() == null) {
			taskfinish.setFinishtime(new Date());
		}
		taskfinishDao.update(taskfinish);
	}
	/**
	 * 任务总数
	 * @return
	 */
	public int getTotalNum() {
		return taskDao.getTotalNum();
	}
	/**
	 * 任务列表
	 * @param start
	 * @param offset
	 * @return
	 */
	public List<Task> getList(int start, int offset) {
		List<Task> list = taskDao.getList(start,offset);
		for(Task task:list) {
			task.setUser(userDao.getById(task.getTeacher_id()));
		}
		return list;
	}
	/**
	 * 关于这个任务的所有任务完成情况数量
	 * @param task_id
	 * @return
	 */
	public int getTotalNumByTask_id(int task_id) {
		return taskfinishDao.getTotalNumByTask_id(task_id);
	}
	/**
	 * 关于这个任务的任务完成情况列表
	 * @param task_id
	 * @param start
	 * @param offset
	 * @return
	 */
	public List<Taskfinish> getTFListByTask_id(int task_id, int start, int offset) {
		List<Taskfinish> list = taskfinishDao.getListByTask_id(task_id,start,offset);
		for(Taskfinish tf:list) {
			tf.setUserByUserId(userDao.getById(tf.getUser_id()));
		}
		return list;
	}
	public Taskfinish getTFById(int tfid) {
		Taskfinish tf =  taskfinishDao.getById(tfid);
		tf.setTask(taskDao.getById(tf.getTask_id()));
		tf.setUserByUserId(userDao.getById(tf.getUser_id()));
		tf.setUserByRemarkuserId(userDao.getById(tf.getRemarkuser_id()));
		return tf;
	}
	public void updateremark(Taskfinish tft) throws ServiceException {
		if(tft == null)
			throw new ServiceException("空的参数");
		if(userDao.getById(tft.getRemarkuser_id()) == null) {
			throw new ServiceException("评价人不存在");
		}
		if(tft.getRemark()!=null && tft.getRemark().length()>300) {
			throw new ServiceException("评价长度超过300");
		}
		taskfinishDao.update(tft);
	}
	public TaskDao getTaskDao() {
		return taskDao;
	}
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public TaskfinishDao getTaskfinishDao() {
		return taskfinishDao;
	}
	public void setTaskfinishDao(TaskfinishDao taskfinishDao) {
		this.taskfinishDao = taskfinishDao;
	}
	
}

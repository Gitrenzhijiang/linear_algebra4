package com.linear.domain;

import java.util.Date;

/**
 * 任务完成
 */
public class Taskfinish implements java.io.Serializable {

	private Integer pk_id;
	private Task task;
	private User userByUserId;
	private User userByRemarkuserId;
	private int status;
	private String remark;
	private String res;
	private Date finishtime;
	private String filepath;
	private int task_id;
	private int user_id;
	private int remarkuser_id;
	public Taskfinish() {
	}

	public Taskfinish(int status) {
		this.status = status;
	}

	

	

	public Integer getPk_id() {
		return pk_id;
	}

	public void setPk_id(Integer pk_id) {
		this.pk_id = pk_id;
	}
	
	public int getTask_id() {
		return task_id;
	}

	public void setTask_id(int task_id) {
		this.task_id = task_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getRemarkuser_id() {
		return remarkuser_id;
	}

	public void setRemarkuser_id(int remarkuser_id) {
		this.remarkuser_id = remarkuser_id;
	}

	public Task getTask() {
		return this.task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public User getUserByUserId() {
		return this.userByUserId;
	}

	public void setUserByUserId(User userByUserId) {
		this.userByUserId = userByUserId;
	}

	public User getUserByRemarkuserId() {
		return this.userByRemarkuserId;
	}

	public void setUserByRemarkuserId(User userByRemarkuserId) {
		this.userByRemarkuserId = userByRemarkuserId;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRes() {
		return this.res;
	}

	public void setRes(String res) {
		this.res = res;
	}

	public Date getFinishtime() {
		return this.finishtime;
	}

	public void setFinishtime(Date finishtime) {
		this.finishtime = finishtime;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

}

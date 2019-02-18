package com.linear.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 任务
 */
public class Task implements java.io.Serializable {

	private Integer pk_id;
	private User user;
	private String name;
	private String descript;
	private String filepath;
	private Date createtime;
	private int teacher_id;
	private List<Taskfinish> taskfinishes = new ArrayList<Taskfinish>();

	public Task() {
	}

	

	public Integer getPk_id() {
		return pk_id;
	}



	public void setPk_id(Integer pk_id) {
		this.pk_id = pk_id;
	}

	

	public int getTeacher_id() {
		return teacher_id;
	}



	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}



	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public List<Taskfinish> getTaskfinishes() {
		return taskfinishes;
	}

	public void setTaskfinishes(List<Taskfinish> taskfinishes) {
		this.taskfinishes = taskfinishes;
	}



	@Override
	public String toString() {
		return "Task [pk_id=" + pk_id + ", user=" + user + ", name=" + name + ", descript=" + descript + ", filepath="
				+ filepath + ", createtime=" + createtime + ", teacher_id=" + teacher_id + ", taskfinishes="
				+ taskfinishes + "]";
	}

	

}

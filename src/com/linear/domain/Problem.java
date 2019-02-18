package com.linear.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 问题
 */
public class Problem implements java.io.Serializable {

	private Integer pk_id;
	private User user;
	private String title;
	private int level;
	private String descript;
	private String qpicpath;
	private int issolved;
	private Date createtime;
	private int user_id;
	private List<Answer> answers = new ArrayList<Answer>();

	public Problem() {
	}

	public Problem(String title, int level, String descript, int issolved, Date createtime) {
		this.title = title;
		this.level = level;
		this.descript = descript;
		this.issolved = issolved;
		this.createtime = createtime;
	}


	

	public Integer getPk_id() {
		return pk_id;
	}
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public void setPk_id(Integer pk_id) {
		this.pk_id = pk_id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getQpicpath() {
		return this.qpicpath;
	}

	public void setQpicpath(String qpicpath) {
		this.qpicpath = qpicpath;
	}

	public int getIssolved() {
		return this.issolved;
	}

	public void setIssolved(int issolved) {
		this.issolved = issolved;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	

}

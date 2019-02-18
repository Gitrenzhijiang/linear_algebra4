package com.linear.domain;

import java.util.Date;

/**
 * 关于回答的讨论
 */
public class Discuss implements java.io.Serializable {

	private Integer pk_id;
	private Answer answer;
	private User user;/*发言人*/
	private Date spoketime;
	private String spokestext;
	private int answer_id;
	private int spokesman_id;
	public Discuss() {
	}

	public Discuss(Date spoketime, String spokestext) {
		this.spoketime = spoketime;
		this.spokestext = spokestext;
	}

	public Discuss(Answer answer, User user, Date spoketime, String spokestext) {
		this.answer = answer;
		this.user = user;
		this.spoketime = spoketime;
		this.spokestext = spokestext;
	}

	public Integer getPk_id() {
		return pk_id;
	}

	public void setPk_id(Integer pk_id) {
		this.pk_id = pk_id;
	}
	

	public int getAnswer_id() {
		return answer_id;
	}

	public void setAnswer_id(int answer_id) {
		this.answer_id = answer_id;
	}

	public int getSpokesman_id() {
		return spokesman_id;
	}

	public void setSpokesman_id(int spokesman_id) {
		this.spokesman_id = spokesman_id;
	}

	public Answer getAnswer() {
		return this.answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getSpoketime() {
		return this.spoketime;
	}

	public void setSpoketime(Date spoketime) {
		this.spoketime = spoketime;
	}

	public String getSpokestext() {
		return this.spokestext;
	}

	public void setSpokestext(String spokestext) {
		this.spokestext = spokestext;
	}

}

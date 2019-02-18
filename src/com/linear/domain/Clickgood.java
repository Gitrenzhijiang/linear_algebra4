package com.linear.domain;

/**
 * 点赞
 */
public class Clickgood implements java.io.Serializable {

	private Integer pk_id;
	private Answer answer;
	private User user;
	private int isgood;
	private int answer_id;
	private int clickuser_id;
	public Clickgood() {
	}

	public Clickgood(int isgood) {
		this.isgood = isgood;
	}

	public Clickgood(Answer answer, User user, int isgood) {
		this.answer = answer;
		this.user = user;
		this.isgood = isgood;
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

	public int getClickuser_id() {
		return clickuser_id;
	}

	public void setClickuser_id(int clickuser_id) {
		this.clickuser_id = clickuser_id;
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

	public int getIsgood() {
		return this.isgood;
	}

	public void setIsgood(int isgood) {
		this.isgood = isgood;
	}

}

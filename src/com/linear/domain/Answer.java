package com.linear.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 问题回答
 */
public class Answer implements java.io.Serializable {

	private Integer pk_id;
	private Problem problem;
	private User user;
	private Date answertime;
	private String apicpath;
	private String answertext;
	private int oknum;
	private int errnum;
	
	private int user_id;
	private int problem_id;
	private List<Clickgood> clickgoods = new ArrayList<Clickgood>();
	private List<Discuss> discusses = new ArrayList<Discuss>();

	public Answer() {
	}

	public Answer(Date answertime, String answertext, int oknum, int errnum) {
		this.answertime = answertime;
		this.answertext = answertext;
		this.oknum = oknum;
		this.errnum = errnum;
	}


	public Integer getPk_id() {
		return pk_id;
	}

	public void setPk_id(Integer pk_id) {
		this.pk_id = pk_id;
	}
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getProblem_id() {
		return problem_id;
	}

	public void setProblem_id(int problem_id) {
		this.problem_id = problem_id;
	}

	public Problem getProblem() {
		return this.problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getAnswertime() {
		return this.answertime;
	}

	public void setAnswertime(Date answertime) {
		this.answertime = answertime;
	}

	public String getApicpath() {
		return this.apicpath;
	}

	public void setApicpath(String apicpath) {
		this.apicpath = apicpath;
	}

	public String getAnswertext() {
		return this.answertext;
	}

	public void setAnswertext(String answertext) {
		this.answertext = answertext;
	}

	public int getOknum() {
		return this.oknum;
	}

	public void setOknum(int oknum) {
		this.oknum = oknum;
	}

	public int getErrnum() {
		return this.errnum;
	}

	public void setErrnum(int errnum) {
		this.errnum = errnum;
	}

	public List<Clickgood> getClickgoods() {
		return clickgoods;
	}

	public void setClickgoods(List<Clickgood> clickgoods) {
		this.clickgoods = clickgoods;
	}

	public List<Discuss> getDiscusses() {
		return discusses;
	}

	public void setDiscusses(List<Discuss> discusses) {
		this.discusses = discusses;
	}
	
}

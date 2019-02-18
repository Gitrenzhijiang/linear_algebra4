package com.linear.domain;

/**
 * 通知查看情况
 */
public class Noticeisread implements java.io.Serializable {

	private Integer pk_id;
	private Notice notice;
	private User user;
	private int isread;
	private int notice_id;
	private int user_id;
	public Noticeisread() {
	}

	public Noticeisread(int isread) {
		this.isread = isread;
	}

	public Noticeisread(Notice notice, User user, int isread) {
		this.notice = notice;
		this.user = user;
		this.isread = isread;
	}

	public Integer getPk_id() {
		return pk_id;
	}

	public void setPk_id(Integer pk_id) {
		this.pk_id = pk_id;
	}
	
	public int getNotice_id() {
		return notice_id;
	}

	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Notice getNotice() {
		return this.notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getIsread() {
		return this.isread;
	}

	public void setIsread(int isread) {
		this.isread = isread;
	}

}

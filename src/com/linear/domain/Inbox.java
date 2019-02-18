package com.linear.domain;

import java.util.Date;

/**
 * 邮件
 */
public class Inbox implements java.io.Serializable {

	private Integer pk_id;
	private User userBySendId;
	private User userByReceiveId;
	private String subject;
	private String message;
	private int isread;
	private Date sendtime;
	private int send_id;
	private int receive_id;
	public Inbox() {
	}

	public Inbox(String subject, String message, int isread, Date sendtime) {
		this.subject = subject;
		this.message = message;
		this.isread = isread;
		this.sendtime = sendtime;
	}

	public Inbox(User userBySendId, User userByReceiveId, String subject, String message, int isread, Date sendtime) {
		this.userBySendId = userBySendId;
		this.userByReceiveId = userByReceiveId;
		this.subject = subject;
		this.message = message;
		this.isread = isread;
		this.sendtime = sendtime;
	}

	public Integer getPk_id() {
		return pk_id;
	}

	public void setPk_id(Integer pk_id) {
		this.pk_id = pk_id;
	}
	
	
	public int getSend_id() {
		return send_id;
	}

	public void setSend_id(int send_id) {
		this.send_id = send_id;
	}

	public int getReceive_id() {
		return receive_id;
	}

	public void setReceive_id(int receive_id) {
		this.receive_id = receive_id;
	}

	public User getUserBySendId() {
		return this.userBySendId;
	}

	public void setUserBySendId(User userBySendId) {
		this.userBySendId = userBySendId;
	}

	public User getUserByReceiveId() {
		return this.userByReceiveId;
	}

	public void setUserByReceiveId(User userByReceiveId) {
		this.userByReceiveId = userByReceiveId;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getIsread() {
		return this.isread;
	}

	public void setIsread(int isread) {
		this.isread = isread;
	}

	public Date getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

}

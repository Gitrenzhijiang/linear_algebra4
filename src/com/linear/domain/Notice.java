package com.linear.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 通知
 */
public class Notice implements java.io.Serializable {

	private Integer pk_id;
	private User user;/*发布通知的人*/
	private String ntitle;
	private String ntext;
	private Date createtime;
	private List<Noticeisread> noticeisreads = null;
	private int createuser_id;
	public Notice() {
	}

	public Notice(String ntitle, String ntext, Date createtime) {
		this.ntitle = ntitle;
		this.ntext = ntext;
		this.createtime = createtime;
	}


	public Integer getPk_id() {
		return pk_id;
	}

	public void setPk_id(Integer pk_id) {
		this.pk_id = pk_id;
	}
	
	

	public int getCreateuser_id() {
		return createuser_id;
	}

	public void setCreateuser_id(int createuser_id) {
		this.createuser_id = createuser_id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNtitle() {
		return this.ntitle;
	}

	public void setNtitle(String ntitle) {
		this.ntitle = ntitle;
	}

	public String getNtext() {
		return this.ntext;
	}

	public void setNtext(String ntext) {
		this.ntext = ntext;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public List<Noticeisread> getNoticeisreads() {
		return noticeisreads;
	}

	public void setNoticeisreads(List<Noticeisread> noticeisreads) {
		this.noticeisreads = noticeisreads;
	}

}

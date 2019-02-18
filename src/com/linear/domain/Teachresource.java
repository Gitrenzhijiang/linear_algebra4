package com.linear.domain;

import java.util.Date;

/**
 * 教学资源
 */
public class Teachresource implements java.io.Serializable {

	private Integer pk_id;
	private User user;/*上传者*/
	private String rname;
	private String rfilepath;
	private String descript;
	private Date uploadtime;
	private int upload_id;

	public Teachresource() {
	}
	
	public Integer getPk_id() {
		return pk_id;
	}



	public void setPk_id(Integer pk_id) {
		this.pk_id = pk_id;
	}



	public int getUpload_id() {
		return upload_id;
	}



	public void setUpload_id(int upload_id) {
		this.upload_id = upload_id;
	}



	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRname() {
		return this.rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getRfilepath() {
		return this.rfilepath;
	}

	public void setRfilepath(String rfilepath) {
		this.rfilepath = rfilepath;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public Date getUploadtime() {
		return this.uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

}

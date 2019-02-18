package com.linear.domain;

/**
 * 算法
 */
public class Agorithm implements java.io.Serializable {

	private Integer pk_id;
	private User user;
	private String name;
	private String filepath;
	private int user_id;

	public Agorithm() {
	}

	public Agorithm(String name, String filepath) {
		this.name = name;
		this.filepath = filepath;
	}

	public Agorithm(User user, String name, String filepath) {
		this.user = user;
		this.name = name;
		this.filepath = filepath;
	}

	public Integer getPk_id() {
		return pk_id;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
}

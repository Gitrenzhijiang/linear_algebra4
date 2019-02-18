package com.linear.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户
 */
public class User implements java.io.Serializable {

	private Integer pk_id;
	private Role role;
	private String user_no;
	private String name;
	private String password;
	private String email;
	private String headpic;
	private String descript;
	private Date ctime;
	private int activation;
	private int role_id;
	public User() {
	}

	public Integer getPk_id() {
		return pk_id;
	}

	public void setPk_id(Integer pk_id) {
		this.pk_id = pk_id;
	}

	

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUser_no() {
		return user_no;
	}


	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHeadpic() {
		return this.headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public int getActivation() {
		return this.activation;
	}

	public void setActivation(int activation) {
		this.activation = activation;
	}

	@Override
	public String toString() {
		return "User [pk_id=" + pk_id + ", role=" + role + ", user_no=" + user_no + ", name=" + name + ", password="
				+ password + ", email=" + email + ", headpic=" + headpic + ", descript=" + descript + ", ctime=" + ctime
				+ ", activation=" + activation + ", role_id=" + role_id + "]";
	}


}

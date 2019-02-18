package com.linear.domain;

/**
 * 资源权限
 */
public class Resource implements java.io.Serializable {

	private Integer pk_id;
	private Role role;
	private String url;
	private String method;
	private int role_id;
	public Resource() {
	}

	public Resource(String url) {
		this.url = url;
	}

	public Resource(Role role, String url, String method) {
		this.role = role;
		this.url = url;
		this.method = method;
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

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}

package com.linear.domain;

/**
 * 角色
 */
public class Role implements java.io.Serializable {

	private Integer pk_id;
	private String name;
	private String descript;

	public Role() {
	}


	

	public Integer getPk_id() {
		return pk_id;
	}




	public void setPk_id(Integer pk_id) {
		this.pk_id = pk_id;
	}




	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}


}

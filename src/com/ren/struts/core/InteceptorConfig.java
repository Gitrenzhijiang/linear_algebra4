package com.ren.struts.core;

public class InteceptorConfig {
	public InteceptorConfig() {
	}
	public InteceptorConfig(String name, String classname) {
		this.name = name;
		this.classname = classname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	private String name;
	private String classname;
}

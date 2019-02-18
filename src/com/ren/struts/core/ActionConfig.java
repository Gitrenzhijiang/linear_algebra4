package com.ren.struts.core;

import java.util.HashMap;
import java.util.Map;
/**
 * action的po对象
 * @author 任志江
 *
 */
public class ActionConfig {
	public ActionConfig() {
		
	}
	public ActionConfig(String name, String method, String classname, Map<String, String> resultMap) {
		this.name = name;
		this.method = method;
		this.classname = classname;
		this.resultMap = resultMap;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public Map<String, String> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, String> resultMap) {
		this.resultMap = resultMap;
	}
	private String name;
	private String method;
	private String classname;
	private Map<String, String> resultMap = new HashMap<String, String>();
	
	
}

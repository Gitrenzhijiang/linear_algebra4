package com.ren.spring.core;

import java.util.ArrayList;
import java.util.List;

public class BeanConfig {
	private String id;
	private String className;
	private String autowire;// no byType byName
	private String scope;//prototype /singleton
	private List<Property> propertyList = new ArrayList<Property>();
	private Object object;
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public String getAutowire() {
		return autowire;
	}


	public void setAutowire(String autowire) {
		this.autowire = autowire;
	}


	public List<Property> getPropertyList() {
		return propertyList;
	}


	public void setPropertyList(List<Property> propertyList) {
		this.propertyList = propertyList;
	}


	public String getScope() {
		return scope;
	}


	public void setScope(String scope) {
		this.scope = scope;
	}


	public Object getObject() {
		return object;
	}


	public void setObject(Object object) {
		this.object = object;
	}

	
	
}
class Property{
	String name;
	String ref;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	
}
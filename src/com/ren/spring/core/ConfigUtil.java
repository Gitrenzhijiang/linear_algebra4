package com.ren.spring.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class ConfigUtil {
	
	/**
	 * 解析一个文件，返回所有的beanConfig
	 * @param fileName
	 * @return
	 */
	public static List<BeanConfig> analyze(String fileName) {
		SAXReader reader = new SAXReader();
		List<BeanConfig> beanList = new ArrayList<BeanConfig>();
		//加载所有的bean
		try {
			Document doc = reader.read(ConfigUtil.class.getClassLoader().getResourceAsStream(fileName));
			Element root = doc.getRootElement();
	        List<Element> list = root.selectNodes("bean");/*得到所有的bean*/
	        for(Element element : list) {
	        	String id = element.attributeValue("id");
	        	String classname = element.attributeValue("class");
	        	String autoWire = element.attributeValue("autowire");
	        	String scope = element.attributeValue("scope");
	        	//默认autoWire是 no
	        	if(autoWire == null || "".equals(autoWire)) {
	        		autoWire = "no";
	        	}
	        	if(scope == null || "".equals(scope)){
	        		scope = "singleton";
	        	}
	        	//构建beanConfig
	        	BeanConfig beanConfig = new BeanConfig();
	        	beanConfig.setId(id);
	        	beanConfig.setClassName(classname);
	        	beanConfig.setAutowire(autoWire);
	        	beanConfig.setScope(scope);
	        	//构建propertyList
	        	List<Property> pList = new ArrayList<Property>(); 
	        	List<Element> pcList = element.elements("property");
	        	for(Element p : pcList) {
	        		String pname = p.attributeValue("name");
	        		String pref = p.attributeValue("ref");
	        		Property property = new Property();
	        		property.setName(pname);
	        		property.setRef(pref);
	        		pList.add(property);
	        	}
	        	beanConfig.setPropertyList(pList);
	        	beanList.add(beanConfig);
	        }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return beanList;
	}
}

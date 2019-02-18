package com.ren.struts.core;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 加载mystruts.xml的配置信息
 * @author 任志江
 *
 */
public class ConfigUtil {
	public static Map<String, ActionConfig> parseActionsConfigFile(String fileName) {
		SAXReader reader = new SAXReader();
		Map<String, ActionConfig> map = new HashMap<>();
		//加载所有的action
		try {
			Document doc = reader.read(ConfigUtil.class.getClassLoader().getResourceAsStream(fileName));
			Element root = doc.getRootElement();
	        List<Element> list = root.selectNodes("package/action");
	        for(Element element : list) {
	        	String name = element.attributeValue("name");
	        	String classname = element.attributeValue("class");
	        	String method = element.attributeValue("method");
	        	Map<String,String> resultMap = new HashMap<String, String>();
	        	ActionConfig actionConfig = new ActionConfig(name, method, classname, resultMap);
	        	
	        	List<Element> resultLists = element.elements("result");
	        	for(Element resultElement : resultLists) {
	        		String resultname = resultElement.attributeValue("name");
	        		String resultvalue = resultElement.getStringValue();
	        		resultMap.put(resultname, resultvalue);
	        	}
	        	
	        	map.put(name, actionConfig);
	        }
		}catch(Exception e) {
			e.printStackTrace();
		}
		return map;
		
	}
	
	public static Map<String, InteceptorConfig> parseInteceptorsConfigFile(String fileName) {
		SAXReader reader = new SAXReader();
		Map<String, InteceptorConfig> map = new LinkedHashMap<String, InteceptorConfig>();
		//加载所有的全局Intceptor
		try {
			Document doc = reader.read(ConfigUtil.class.getClassLoader().getResourceAsStream(fileName));
			Element root = doc.getRootElement();
	        List<Element> list = root.selectNodes("package/inteceptor");
	        for(Element element : list) {
	        	String name = element.attributeValue("name");
	        	String classname = element.attributeValue("class");
	        	InteceptorConfig ic = new InteceptorConfig(name, classname);
	        	map.put(name, ic);
	        }
	        
		}catch(Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}

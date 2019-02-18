package com.ren.struts.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action的运行环境
 * @author 任志江
 *
 */
public class ActionContext {

	private ActionConfig actionfig;
	private HttpServletRequest req;
	private HttpServletResponse res;
	private String root;
	public ActionContext(HttpServletRequest req, HttpServletResponse res) {
		this.req = req;
		this.res = res;
		this.root = req.getServletContext().getContextPath();
	}
	

	public ActionConfig getActionfig() {
		return actionfig;
	}
	
	public HttpServletRequest getReq() {
		return req;
	}

	public HttpServletResponse getRes() {
		return res;
	}
	public String getRoot() {
		return root;
	}
	/**
	 * 预先查看url，是否包含.action
	 * @param url
	 * @return
	 */
	public static boolean preparseUrl(String url) {
		if(url != null && url.contains(".action"))
			return true;
		return false;
	}
	/**
	 * 解析url从而查询执行那个action
	 * 如果不存在，返回false
	 * 这个函数相当于绑定了这个Action和actionContext
	 * @param url
	 * @param req
	 * @return
	 */

	public boolean parseUrl(String url) {
		if(url == null || req == null) {
			return false;
		}
		if(url.contains(".action!") || url.endsWith(".action")) {
			String root = req.getServletContext().getContextPath();
			String actionName = url.replace(".action", "").replace(root + "/", "");
			if(actionName.contains("!")) {
				actionName = actionName.substring(0, actionName.indexOf("!"));
			}
			//查询actionName是否存在
			if(acMap.containsKey(actionName)) {
				this.actionfig = acMap.get(actionName);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 加载所有的公有的inteceptorConfig和ActionConfig
	 * @param acMap
	 * @param icMap
	 */
	public static void parseConfigMap(Map<String,ActionConfig> acMap,Map<String,InteceptorConfig>icMap) {
		//初始化所有的额actionConfig
		if(acMap != null) {
			if(ActionContext.acMap == null)
				ActionContext.acMap = acMap;
			else {
				ActionContext.acMap.putAll(acMap);
			}
		}
		//初始化所有的拦截器配置
		if(icMap != null) {
			if(ActionContext.icMap == null) {
				ActionContext.icMap = icMap;
			}else {
				ActionContext.icMap.putAll(icMap);
			}
		}
		
	}
	
	/**
	 * 返回过滤器
	 * @param ic
	 * @return
	 */
	private static Inteceptor getInteceptor(InteceptorConfig ic) {
		Inteceptor it = null;
		String name = ic.getName();
		//从外部容器中获得inteceptor,获得的拦截器都是用户自己定义的
		if(getActionAndInteceptor != null) {
			it = getActionAndInteceptor.getInteceptor(name);
			if(it != null)
				return it;
		}
		//struts-default.xml中的Inteceptor
		if(inteceptorMap.containsKey(name) == true) {
			return inteceptorMap.get(name);
		}
		try {
			it = (Inteceptor) Class.forName(ic.getClassname()).newInstance();
			inteceptorMap.put(name, it);//放入map中
		}catch(Exception e) {
			e.printStackTrace();
		}
		return it;
	}
	//返回struts默认的inteceptor 和 自定义的inteceptor
	public static List<Inteceptor> getInteceptors(){
		
		List<Inteceptor> list = new ArrayList<Inteceptor>();
		for (Iterator it = icMap.keySet().iterator(); it.hasNext();) {
			String key = (String) it.next();
			list.add(getInteceptor(icMap.get(key)));//有配置获得inteceptor
		}
		return list;
	}
	//保存所有的struts-default.xml内的inteceptor
	private static Map<String, Inteceptor> inteceptorMap = new LinkedHashMap<String, Inteceptor>();
	private static Map<String, ActionConfig> acMap = null;
	private static Map<String, InteceptorConfig> icMap = null;
	
	/**
	 * 为了可以使用struts-spring插件
	 */
	public static GetActionAndInteceptor getActionAndInteceptor = null;//外部的程序可以设定这个的值
	/**
	 * 返回Action
	 * @return
	 */
	public Action getAction() {
		if(this.action != null)//action 已经得到
			return this.action;
		if(getActionAndInteceptor == null) {//struts自己创建
			try {
				this.action = (Action)Class.forName(actionfig.getClassname()).newInstance();
			}catch(Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}else {//外部的程序创建action
			this.action = getActionAndInteceptor.getAction(actionfig.getName());
		}
		return this.action;
	}
	private Action action;
}

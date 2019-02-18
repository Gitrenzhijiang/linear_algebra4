package com.ren.spring.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.linear.action.UserAction;

public class ClassPathXmlApplicationContext implements ApplicationContext{
	/**
	 * bean 的作用域
	 */
	public static final String Scope_prototype = "prototype";
	public static final String Scope_singleton = "singleton";
	/**
	 * bean 的自动装配模式
	 */
	public static final String AutoWire_byType = "byType";
	public static final String AutoWire_byName = "byName";
	
	public ClassPathXmlApplicationContext(String xmlName) {
		init(ConfigUtil.analyze(xmlName));
	}
	/**
	 * 得到bean
	 * @param id
	 * @return
	 */
	public Object getBean(String id) {
		Object res = null;
		BeanConfig beanConfig = map.get(id);
		if(beanConfig != null) {
			//判断配置的bean的作用域
			if("prototype".equals(beanConfig.getScope())) {
				res = newInstance(beanConfig.getClassName());
				//注入
				try {
					autoWire(res, beanConfig);
				}catch(Exception e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}else {//单例设计模式
				res = beanConfig.getObject();
			}
		}
		return res;
	}
	
	
	private void init(List<BeanConfig> beanConfigList) {
		// 解读所有的bean
		if (beanConfigList != null && beanConfigList.size() > 0) {
			for (BeanConfig beanConfig : beanConfigList) {
				map.put(beanConfig.getId(), beanConfig);
				if(Scope_singleton.equals(beanConfig.getScope())) {//对于单例的bean,现在就创建
					beanConfig.setObject(newInstance(beanConfig.getClassName()));
				}
			}
			for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
				String id = (String) iterator.next();
				BeanConfig bc = map.get(id);
				if(Scope_singleton.equals(bc.getScope())) {
					//给单例注入
					try {
						autoWire(bc.getObject(), bc);
					}catch(Exception e) {
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
			}
		}
	}
	/**
	 * 创建一个对象,被创建的对象必须有一个无参数的构造函数
	 * @param className
	 * @return
	 */
	private Object newInstance(String className) {
		Object obj = null;
		try {
			obj = Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return obj;
	}
	/**
	 * 给这个obj注入
	 * @param obj
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 */
	private void autoWire(Object obj, BeanConfig beanConfig) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		Class clazz = obj.getClass();//取得类型
		//如果配置了property
		for(Property p:beanConfig.getPropertyList()) {
			String pname = p.getName();
			pname = pname.substring(0, 1).toUpperCase() + pname.substring(1);
			if(pname != null && !"".equals(pname)) {
				//遍历所有的method,找到方法名为set+pname的方法
				for(Method m:clazz.getDeclaredMethods()) {
					if(("set" + pname).equals(m.getName()) && m.getParameterTypes().length == 1) {
						m.invoke(obj, getBean(p.ref));
						break;
					}
				}
			}
		}
		//如果配置了byType
		if(AutoWire_byType.equals(beanConfig.getAutowire())) {
			autowire_byType(obj, beanConfig);
		}else if(AutoWire_byName.equals(beanConfig.getAutowire())) {//byName
			autowire_byName(obj);
		}
	}
	
	/**
	 * 给obj的所有set方法，按照Name装配
	 * 当name 匹配 id 时，装配
	 * @param ms
	 * @param obj
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	private void autowire_byName(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(obj == null)
			return;
		Method [] ms = obj.getClass().getDeclaredMethods();
		for(Method method : ms) {
			String methodName  = method.getName();
			if(methodName.startsWith("set") && methodName.length() > 3 && method.getParameterTypes().length == 1) {
				methodName = methodName.substring(3);
				methodName = methodName.substring(0, 1).toLowerCase() + methodName.substring(1);
				Object value = getBean(methodName);
				if(value != null) {//如果存在
					method.invoke(obj, value);
				}
			}
		}
	}
	/**
	 * 根据类型注入
	 * @param ms
	 * @param obj
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws ClassNotFoundException 
	 */
	private void autowire_byType(Object obj, BeanConfig beanConfig) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		if(obj == null)
			return;
		Method [] ms = obj.getClass().getDeclaredMethods();
		for(Method method : ms) {
			String methodName  = method.getName();
			if(methodName.startsWith("set") && methodName.length() > 3) {
				//查看参数类型数组
				Class czs[] = method.getParameterTypes();
				if(czs != null && czs.length == 1) {
					//遍历容器内所有的值,看是否有这个类型,或者是这个类型的子类
					for (Iterator it = map.keySet().iterator(); it.hasNext();) {
						String id = (String) it.next();
						if(id.equals(beanConfig.getId()))
							continue;
						Class clazz = Class.forName(map.get(id).getClassName());
						
						if(czs[0].isAssignableFrom(clazz)) {//找到了
							method.invoke(obj, getBean(id));
							break;
						}
					}
				}
			}
		}
	}
	private Map<String, BeanConfig> map = new HashMap<>();
	
	//删除所有引用
	@Override
	public void destory() {
		map = null;
		System.gc();
	}
	
	
	
}








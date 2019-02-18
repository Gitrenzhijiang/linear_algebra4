package com.ren.struts.core;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class BeanUtil {
	public static  void request2Action(HttpServletRequest req, Action action) {
		try {
			Class clazz = action.getClass();
			Method [] methods = clazz.getMethods();
			for(Method method : methods) {
				String methodName = method.getName();
				if(methodName.startsWith("set")) {
					methodName = methodName.substring(3).toLowerCase();
					Class fieldClassName = clazz.getDeclaredField(methodName).getType();
					//看能否把string -- > 对应类型
					String [] values = req.getParameterValues(methodName);
					if(values != null)
						method.invoke(action, BeanUtil.transefer(fieldClassName, values));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void action2Request(Action action, HttpServletRequest req) {
		try {
			Class clazz = action.getClass();
			Method [] methods = clazz.getMethods();
			for(Method method : methods) {
				String methodName = method.getName();
				if(methodName.startsWith("get")) {
					methodName = methodName.substring(3).toLowerCase();
					Object obj = method.invoke(action, null);
					req.setAttribute(methodName, obj);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Object[]  transefer(Class fieldType, String[] values) {
		Object[] obs = null;
		String typeString = fieldType.getSimpleName().replace("[]", "");
		if("String".equalsIgnoreCase(typeString)) {
			obs = values;
		}else if("int".equalsIgnoreCase(typeString) || "Integer".equals(typeString)) {
			obs = new Integer[values.length];
			for(int i = 0;i < obs.length;i++) {
				obs[i] = Integer.parseInt(values[i]);
			}
		}else if("float".equalsIgnoreCase(typeString) || "Float".equals(typeString)) {
			obs = new Float[values.length];
			for(int i = 0;i < obs.length;i++) {
				obs[i] = Float.parseFloat(values[i]);
			}
		}else if("double".equalsIgnoreCase(typeString) || "Double".equals(typeString)) {
			obs = new Double[values.length];
			for(int i = 0;i < obs.length;i++) {
				obs[i] = Double.parseDouble(values[i]);
			}
		}else if("date".equalsIgnoreCase(typeString)) {// 2016-12-12 12:12:12
			obs = new Date[values.length];
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(int i = 0;i < obs.length;i++) {
				try {
					obs[i] = sdf.parse(values[i]);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		
		return obs;
	}
}



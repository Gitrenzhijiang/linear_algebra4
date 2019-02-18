package com.linear.action;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class ErrorsToReqUtil {
	/**
	 * 将errs中的信息放入request域中
	 * @param errs
	 * @param req
	 */
	public static void toReq(Map<String, String> errs, HttpServletRequest req) {
		if(errs != null && !errs.isEmpty()) {
			for (Iterator iterator = errs.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				req.setAttribute(key, errs.get(key));
			}
		}
	}
	/**
	 * 将getParameter的值放入attribute中
	 * 一个name对应一个value的参数将被放入attribute中
	 * 如果一个name 不是对应一个value ,它将不放入
	 * 注意:被放入attribute的仍然是字符串类型
	 * @param req
	 */
	public static void reqP2A(HttpServletRequest req) {
		if(req!=null) {
			Map<String, String[]> map = req.getParameterMap();
			if(map != null && map.size() > 0) {
				for (Iterator it = map.keySet().iterator(); it.hasNext();) {
					String key = (String) it.next();
					String [] values = map.get(key);
					if(values != null && values.length == 1) {
						req.setAttribute(key, values[0]);
					}
				}
			}
		}
	}
}

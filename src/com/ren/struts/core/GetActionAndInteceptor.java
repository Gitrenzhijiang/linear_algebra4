package com.ren.struts.core;
/**
 * 这个接口定义了返回Action和Inteceptor的方法，Mystruts外部的程序可以实现这个接口，从而更加高效的返回Action
 */
public interface GetActionAndInteceptor {
	/**
	 * 返回Action
	 * @param actionName
	 * @return
	 */
	Action getAction(String actionName);
	/**
	 * 如果用户自定义了这个拦截器,返回它
	 * @param inteceptorName
	 * @return
	 */
	Inteceptor getInteceptor(String inteceptorName);
}

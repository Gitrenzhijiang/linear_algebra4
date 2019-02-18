package com.ren.struts.core;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyDispatcherFilter implements Filter {

    public MyDispatcherFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		//解析url
		String url = req.getRequestURI();
		
		if(ActionContext.preparseUrl(url)==false) {
			chain.doFilter(req, res);
			return;
		}
		ActionContext actionContext = new ActionContext(req, res);
		if(actionContext.parseUrl(url)) {
			
			ActionInvocation actionInvocation = new ActionInvocation(actionContext);
			actionInvocation.invoke();
			
		}else {
			chain.doFilter(req, res);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		//加载配置文件
		ActionContext.parseConfigMap(ConfigUtil.parseActionsConfigFile("mystruts.xml"),
				ConfigUtil.parseInteceptorsConfigFile("struts-default.xml"));
		ActionContext.parseConfigMap(null,
				ConfigUtil.parseInteceptorsConfigFile("mystruts.xml"));
	}
}

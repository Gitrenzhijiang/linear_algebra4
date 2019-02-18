package com.ren.spring.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.ren.struts.core.ActionContext;

/**
 * Application Lifecycle Listener implementation class ContextLoaderListener
 *
 */
@WebListener
public class ContextLoaderListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ContextLoaderListener() {
       
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    	((GetActionAndInteceptorImp)ActionContext.getActionAndInteceptor).getApplicationContext().destory();
    }

	/**
     * @see servlet容器初始化完毕
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	ActionContext.getActionAndInteceptor = new GetActionAndInteceptorImp();
    }
	
}

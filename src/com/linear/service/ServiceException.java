package com.linear.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceException extends Exception{
	public ServiceException() {}
	
	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(String errName, String errValue) {
		super();
		errMap.put(errName, errValue);
	}
	/**
	 * 保存错误消息键值对
	 * @param errName
	 * @param errValue
	 */
	public void addErrorMessage(String errName, String errValue) {
		errMap.put(errName, errValue);
	}
	/**
	 * 取出错误内容
	 * @param errName
	 * @return
	 */
	public String getErrorMessage(String errName) {
		return errMap.get(errName);
	}
	/**
	 * 当前是否发生了错误
	 * @return
	 */
	public boolean hasError() {
		if(errMap.isEmpty()) {
			return false;
		}
		return true;
	}
	/**
	 * 返回所有错误
	 * @return
	 */
	public Map<String, String> getErrMap() {
		return this.errMap;
	}
	private Map<String, String> errMap = new HashMap<String,String>();
}

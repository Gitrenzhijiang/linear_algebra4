package com.linear.service;

import java.util.Date;
import java.util.List;

import com.linear.dao.TeachresourceDao;
import com.linear.dao.UserDao;
import com.linear.domain.Teachresource;

public class TeachresourceService {
	/**
	 * 教学资源总数目
	 * @return
	 */
	public int getTotalNum() {
		return teachresourceDao.getTotalNum();
	}
	private TeachresourceDao teachresourceDao;
	private UserDao userDao;
	/**
	 * 教学资源列表
	 * 按照上传时间降序排列
	 * @param start
	 * @param offset
	 * @return
	 */
	public List<Teachresource> getList(int start, int offset) {
		List<Teachresource> list = teachresourceDao.getList(start, offset);
		for(Teachresource t:list) {
			t.setUser(userDao.getById(t.getUpload_id()));
		}
		return list;
	}
	/**
	 * 上传
	 * @param ts
	 * @throws ServiceException
	 */
	public void add(Teachresource ts) throws ServiceException {
		//校验数据
		if(ts == null) {
			throw new ServiceException("传入参数为空");
		}
		if(ts.getRname() == null || "".equals(ts.getRname())) {
			throw new ServiceException("资源名称为空");
		}
		if(ts.getRname().length()>30) {
			throw new ServiceException("资源名称长度超过30");
		}
		if(ts.getDescript() == null || "".equals(ts.getDescript())) {
			throw new ServiceException("资源描述为空");
		}
		if(ts.getDescript().length()>300) {
			throw new ServiceException("资源描述长度超过300");
		}
		if(ts.getRfilepath() == null || "".equals(ts.getRfilepath())) {
			throw new ServiceException("资源文件为空");
		}
		if(userDao.getById(ts.getUpload_id()) == null) {
			throw new ServiceException("资源上传者不存在");
		}
		if(ts.getUploadtime() == null)
			ts.setUploadtime(new Date());
		teachresourceDao.add(ts);
	}
	public Teachresource getById(int pk_id) {
		return teachresourceDao.getById(pk_id);
	}
	public int getTotalNumByUpload_id(int upload_id) {
		return teachresourceDao.getTotalNumByUpload_id(upload_id);
	}
	public List<Teachresource> getListByUpload_id(int upload_id, int limit_start, int limit_offset) {
		List<Teachresource> list = teachresourceDao.getListByUpload_id(upload_id, limit_start, limit_offset);
		for(Teachresource t:list) {
			t.setUser(userDao.getById(t.getUpload_id()));
		}
		return list;
	}
	public void deleteById(int pk_id) {
		teachresourceDao.deleteById(pk_id);
	}
	public TeachresourceDao getTeachresourceDao() {
		return teachresourceDao;
	}
	public void setTeachresourceDao(TeachresourceDao teachresourceDao) {
		this.teachresourceDao = teachresourceDao;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}

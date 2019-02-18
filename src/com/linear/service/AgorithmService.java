package com.linear.service;

import java.util.List;

import com.linear.dao.AgorithmDao;
import com.linear.dao.DaoException;
import com.linear.dao.UserDao;
import com.linear.domain.Agorithm;

public class AgorithmService {
	public void add(Agorithm ago) throws ServiceException {
		//验证
		if(ago == null) {
			throw new ServiceException("空的Agorithm参数");
		}
		if(ago.getFilepath() == null || "".equals(ago.getFilepath())) {
			throw new ServiceException("上传的文件为空");
		}
		if(ago.getName()==null || "".equals(ago.getName())) {
			throw new ServiceException("算法名称为空");
		}
		if(ago.getName().length()>30) {
			throw new ServiceException("算法名称超过30");
		}
		if(userDao.getById(ago.getUser_id()) == null) {
			throw new ServiceException("不存在的用户");
		}
		agorithmDao.add(ago);
	}
	private UserDao userDao;
	private AgorithmDao agorithmDao;
	/**
	 * 该用户算法总数
	 * @param user_id
	 * @return
	 */
	public int getTotalNumByUser_id(int user_id) {
		
		return agorithmDao.getTotalNumByUser_id(user_id);
	}
	/**
	 * 该用户的所有算法
	 * @param user_id
	 * @param limit_start
	 * @param limit_offset
	 * @return
	 */
	public List<Agorithm> getListByUser_id(int user_id, int limit_start, int limit_offset) {
		return agorithmDao.getListByUser_id(user_id,limit_start,limit_offset);
	}
	public Agorithm getById(int ago_id) {
		return agorithmDao.getById(ago_id);
	}
	public void deleteById(int ago_id) {
		try {
			if(agorithmDao.getById(ago_id) != null) {
				agorithmDao.deleteById(ago_id);
			}
		} catch (DaoException e) {
		}
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public AgorithmDao getAgorithmDao() {
		return agorithmDao;
	}
	public void setAgorithmDao(AgorithmDao agorithmDao) {
		this.agorithmDao = agorithmDao;
	}

}

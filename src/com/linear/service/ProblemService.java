package com.linear.service;

import java.util.Date;
import java.util.List;

import com.linear.dao.ProblemDao;
import com.linear.dao.UserDao;
import com.linear.domain.Problem;

public class ProblemService {
	
	public List<Problem> getList(int start, int num){
		List<Problem> list = problemDao.getList(start, num);
		for(Problem p : list) {
			p.setUser(userDao.getById(p.getUser_id()));
		}
		return list;
	}
	public int getProblemNum() {
		return problemDao.getProblemNum();
	}
	private ProblemDao problemDao;
	private UserDao userDao;
	public int getProblemNumOfLevel(int level) {
		return problemDao.getProblemNumOfLevel(level);
	}
	public int getProblemNumOfIssolved(int issolved) {
		return problemDao.getProblemNumOfIssolved(issolved);
	}
	public List<Problem> getListOfLevel(int level, int limit_start, int limit_offset) {
		
		List<Problem> list = problemDao.getListOfLevel(level, limit_start,limit_offset);
		for(Problem p : list) {
			p.setUser(userDao.getById(p.getUser_id()));
		}
		return list;
	}
	public List<Problem> getListOfIssolved(int issolved, int limit_start, int limit_offset) {
		List<Problem> list = problemDao.getListOfIssolved(issolved, limit_start, limit_offset);
		for(Problem p : list) {
			p.setUser(userDao.getById(p.getUser_id()));
		}
		return list;
	}
	public Problem getById(int pid) {
		Problem p = problemDao.getById(pid);
		p.setUser(userDao.getById(p.getUser_id()));
		return p;
	}
	public void add(Problem problem) throws ServiceException {
		//验证
		if(problem.getTitle() == null || "".equals(problem.getTitle())) {
			throw new ServiceException("标题为空");
		}
		if(problem.getTitle().length() > 30) {
			throw new ServiceException("标题长度超过30");
		}
		if(problem.getDescript()==null || "".equals(problem.getDescript())) {
			throw new ServiceException("问题描述为空");
		}
		if(problem.getDescript().length() > 300) {
			throw new ServiceException("问题描述长度超过300");
		}
		if(userDao.getById(problem.getUser_id()) == null) {
			throw new ServiceException("该用户不存在");
		}
		if(problem.getCreatetime() == null) {
			problem.setCreatetime(new Date());
		}
		problemDao.add(problem);
	}
	/**
	 * 该用户提问总数
	 * @param pk_id
	 * @return
	 */
	public int getProblemNumByUser_id(int user_id) {
		return problemDao.getProblemNumByUser_id(user_id);
	}
	public List<Problem> getListByUser_id(int user_id, int limit_start, int limit_offset) {
		List<Problem> list = problemDao.getListOfUser_id(user_id, limit_start, limit_offset);
		for(Problem p : list) {
			p.setUser(userDao.getById(p.getUser_id()));
		}
		return list;
	}
	/**
	 * 设置难度等级
	 * @param problem_id
	 * @param level
	 */
	public void setlevel(int problem_id, int level) {
		//验证
		Problem p = getById(problem_id);
		if(p != null) {
			if(level == 0 || level == 1 || level == 2 || level == 3)
				problemDao.updatelevel(p.getPk_id(), level);
		}
	}
	/**
	 * 设置已解决
	 * @param problem_id
	 */
	public void setissolved(int problem_id) {
		Problem p = getById(problem_id);
		if(p != null) {
			problemDao.updateissolved(problem_id, 1);
		}
	}
	public ProblemDao getProblemDao() {
		return problemDao;
	}
	public void setProblemDao(ProblemDao problemDao) {
		this.problemDao = problemDao;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}

package com.linear.service;

import java.util.Date;
import java.util.List;

import com.linear.dao.AnswerDao;
import com.linear.dao.ClickgoodDao;
import com.linear.dao.DiscussDao;
import com.linear.dao.ProblemDao;
import com.linear.dao.UserDao;
import com.linear.domain.Answer;
import com.linear.domain.Discuss;

public class AnswerService {
	/**
	 * 返回该问题的回答列表，回答的排列顺序根据回答的时间降序排列
	 * @param pid
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Answer> getListByProblem_id(int pid,int start, int end){
		List<Answer> list = answerDao.getList(pid,start,end);
		for(Answer a:list) {
			a.setUser(userDao.getById(a.getUser_id()));
			//给oknum和errnum赋值
			a.setOknum(cgd.getNumOfOkByAnswer_id(a.getPk_id()));
			a.setErrnum(cgd.getNumOfErrorByAnswer_id(a.getPk_id()));
			//点赞和踩蛋列表
			a.setClickgoods(cgd.getByAnswer_id(a.getPk_id()));
			//评论列表加载
			a.setDiscusses(discussDao.getListByAnswer_id(a.getPk_id()));
			//评论列表加载用户
			for(Discuss dis:a.getDiscusses()) {
				dis.setUser(userDao.getById(dis.getSpokesman_id()));
			}
		}
		return list;
	}
	
	private AnswerDao answerDao;
	private UserDao userDao;
	private ClickgoodDao cgd;
	private DiscussDao discussDao;
	public int getTotalAnswerNumOfProblemId(int pid) {
		return answerDao.getTotalAnswerNumOfProblemId(pid);
	}
	public void clickOfisgoodToAnswer(int a_id,int user_id, int is_good) throws ServiceException {
		ServiceException se = new ServiceException();
		if(answerDao.getById(a_id) == null || userDao.getById(user_id) == null) {
			se.addErrorMessage("ps_err", "无效的参数");
		}
		//如果某用户已经点赞或者鸡蛋给某个回答
		if(cgd.getByUser_idAndAnswer_id(user_id, a_id) != null) {
			se.addErrorMessage("areade_err", "已经click");
			
		}
		if(se.hasError()) {
			throw se;
		}
		cgd.clickOfisgoodToAnswerByUser_id(a_id,user_id, is_good);
		
	}
	public int getOknumByAnswer_id(int a_id) {
		return cgd.getNumOfOkByAnswer_id(a_id);
	}
	public int getErrnumByAnswer_id(int a_id) {
		return cgd.getNumOfErrorByAnswer_id(a_id);
	}
	
	public void addDisscuss(Discuss discuss) throws ServiceException {
		ServiceException se  = new ServiceException();
		//检查参数
		if(answerDao.getById(discuss.getAnswer_id()) == null) {
			throw new ServiceException("无效的回答");
		}
		if(userDao.getById(discuss.getSpokesman_id()) == null) {
			throw new ServiceException("无效的回答人");
		}
		if(discuss.getSpokestext() == null || "".equals(discuss.getSpokestext())) {
			throw new ServiceException("评论不能为空");
		}
		if(discuss.getSpokestext().length() > 300) {
			throw new ServiceException("评论长度超过300");
		}
		if(discuss.getSpoketime() == null) {
			discuss.setSpoketime(new Date());
		}
		answerDao.addDiscuss(discuss);
	}
	public void addAnswer(Answer answer) throws ServiceException {
		//验证参数problem_id,user_id,answertime,apicpath,answertext
		if(answer == null) {
			throw new ServiceException("空的参数");
		}
		if(answer.getAnswertime() == null) {
			throw new ServiceException("时间为空");
		}
		if(answer.getAnswertext()==null || "".equals(answer.getAnswertext())) {
			throw new ServiceException("内容为空");
		}
		if(answer.getAnswertext().length() > 300) {
			throw new ServiceException("内容长度超过300");
		}
		//验证是否存在problem_id,user_id
		if(problemDao.getById(answer.getProblem_id()) == null) {
			throw new ServiceException("该问题不存在");
		}
		if(userDao.getById(answer.getUser_id()) == null) {
			throw new ServiceException("不存在的用户ID");
		}
		answerDao.add(answer);
	}
	
	private ProblemDao problemDao;
	public AnswerDao getAnswerDao() {
		return answerDao;
	}
	public void setAnswerDao(AnswerDao answerDao) {
		this.answerDao = answerDao;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public ClickgoodDao getCgd() {
		return cgd;
	}
	public void setCgd(ClickgoodDao cgd) {
		this.cgd = cgd;
	}
	public DiscussDao getDiscussDao() {
		return discussDao;
	}
	public void setDiscussDao(DiscussDao discussDao) {
		this.discussDao = discussDao;
	}
	public ProblemDao getProblemDao() {
		return problemDao;
	}
	public void setProblemDao(ProblemDao problemDao) {
		this.problemDao = problemDao;
	}
	
	
}

package com.linear.service;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.linear.dao.UserDao;
import com.linear.domain.User;

public class UserService {
	public static void main(String[] args) throws ServiceException {
		System.out.println(new UserService().login("20162149", "123"));
	}
	/**
	 * 登陆
	 * @param no  账号
	 * @param ps  密码
	 * @return user对象 
	 * 登陆成功，返回user
	 * 如果登陆失败，抛出对应的异常信息列表
	 * @throws ServiceException 
	 */
	public User login(String no, String ps) throws ServiceException {
		//校验所有的字段
		User user = null;
		ServiceException se = new ServiceException();
		if(no == null || "".equals(no)) {
			se.addErrorMessage("user_no_err", "账号为空");
		}
		if(ps == null || "".equals(ps)) {
			se.addErrorMessage("password_err", "密码为空");
		}
		if(se.hasError()) {
			throw se;
		}
		user = userDao.getByUser_no(no);
		if(user == null) {
			se.addErrorMessage("user_no_err", "账号不存在");
		}else if(user.getActivation() != 1){
			if(user.getActivation() == 0)
				se.addErrorMessage("user_no_err", "账号未激活");
			else if(user.getActivation() == 11) {
				se.addErrorMessage("user_no_err", "账号尚未通过审批");
			}
		}else if(user.getActivation() == 1) {
			//检查密码
			if(!user.getPassword().equals(ps))
				se.addErrorMessage("password_err", "密码不正确");
		}
		if(se.hasError()) {
			throw se;
		}
		user.setRole(roleService.getById(user.getRole_id()));
		return user;
	}
	/**
	 * 注册
	 * @param user
	 * @throws ServiceException 
	 */
	public void regist(User user) throws ServiceException {
		//校验所有字段
		ServiceException se = new ServiceException();
		if(user.getUser_no() == null || "".equals(user.getUser_no())) {
			se.addErrorMessage("user_no_err", "账号不能为空");
		}else if(user.getUser_no().length() > 16) {
			se.addErrorMessage("user_no_err", "账号不能超过16位");
		}
		if(se.hasError()) {
			throw se;
		}
		//检验姓名字段
		if(user.getName() == null || "".equals(user.getName())) {
			se.addErrorMessage("name_err", "姓名不能为空");
		}else if(user.getName().length() > 20) {
			se.addErrorMessage("name_err", "姓名不能超过20位");
		}
		if(se.hasError()) {
			throw se;
		}
		//邮件
		String emailstr = user.getEmail();
		String p = "^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
		if(emailstr == null || "".equals(emailstr)) {
			se.addErrorMessage("email_err", "邮件不能为空");
		}else if(emailstr.length() > 50) {
			se.addErrorMessage("email_err", "邮件不能超过50位");
		}else if(Pattern.matches(p, emailstr) == false) {
			se.addErrorMessage("email_err", "邮件无效");
		}
		if(se.hasError()) {
			throw se;
		}
		//密码
		String ps = user.getPassword();
		if(ps == null || "".equals(ps)) {
			se.addErrorMessage("password_err", "密码不能为空");
		}else if(ps.length() > 16) {
			se.addErrorMessage("password_err", "密码不能超过16位");
		}
		if(se.hasError()) {
			throw se;
		}
		//逻辑检验
		//检验是否已经有user_no
		User checkuser = userDao.getByUser_no(user.getUser_no());
		if(checkuser != null) {
			se.addErrorMessage("user_no_err", "该用户已经被注册");
			throw se;
		}
		userDao.add(user);
	}
	/**
	 * 激活
	 * @param pk_id
	 * @throws ServiceException 
	 */
	public void mailcheck(String no) throws ServiceException {
		if(no==null || "".equals(no)) {
			throw new ServiceException("mailcheck_err", "激活对象不存在");
		}
		User u = userDao.getByUser_no(no);
		if(u == null) {
			throw new ServiceException("mailcheck_err", "激活对象不存在");
		}
		
		if(u.getActivation() == 1) {
			throw new ServiceException("mailcheck_err", "邮箱验证已经成功");
		}
		//根据权限，设置
		if(u.getRole_id() == roleService.getStudentRole().getPk_id())
			u.setActivation(1);
		else {
			u.setActivation(11);
			u.setCtime(new Date());//教师，还需要通过管理员验证，所以更新ctime为当前时间
		}
		userDao.update(u);
	}
	private UserDao userDao;
	private RoleService roleService;
	public User getById(int user_id) {
		return userDao.getById(user_id);
	}
	public User getByNo(String user_no) {
		return userDao.getByUser_no(user_no);
	}
	public void update(User my) throws ServiceException {
		if(my == null) {
			throw new ServiceException("参数为空");
		}
		if(my.getName()==null || "".equals(my.getName())) {
			throw new ServiceException("name_err","姓名为空");
		}
		if(my.getName().length() > 20) {
			throw new ServiceException("name_err","姓名长度超过20");
		}
		if(my.getPassword()==null || "".equals(my.getPassword())) {
			throw new ServiceException("password_err","密码为空");
		}
		if(my.getPassword().length() > 16) {
			throw new ServiceException("password_err","密码长度超过16");
		}
		if(my.getEmail()==null || "".equals(my.getEmail())) {
			throw new ServiceException("email_err","邮箱为空");
		}
		if(my.getEmail().length() > 50) {
			throw new ServiceException("email_err","邮箱长度超过50");
		}
		if(my.getDescript().length() > 300) {
			throw new ServiceException("descript_err","个人简介长度超过300");
		}
		
		userDao.update(my);
	}
	/**
	 * 返回教师角色所有通过或者未通过的账号
	 * @param tgnum 1 通过审批    0 未通过审批
	 * @return
	 */
	public List<User> getTeaListByTG(int tgnum, int start, int offset) {
		int status = 11;
		if(tgnum == 1)
			status = 1;
		return userDao.getTeaListByActivation(status, start, offset); 
	}
	public int getTeaNumByTG(int tgnum) {
		int status = 11;
		if(tgnum == 1)
			status = 1;
		return userDao.getTeaNumByActivation(status);
	}
	/**
	 * 审批通过
	 * @param user
	 */
	public void signupcheck(User user) {
		if(user != null) {
			if(user.getRole_id() == roleService.getTeacherRole().getPk_id()) {
				if(user.getActivation() == 11) {
					user.setActivation(1);
					userDao.update(user);
				}
			}
		}
	}
	/**
	 * 管理员审批删除
	 * @param user
	 */
	public void signupdelete(User user) {
		if(user != null) {
			userDao.deleteByPk_id(user.getPk_id());
		}
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public RoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
}

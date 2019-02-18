package com.linear.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linear.domain.Inbox;
import com.linear.domain.Notice;
import com.linear.domain.PageBean;
import com.linear.domain.Problem;
import com.linear.domain.Task;
import com.linear.domain.User;
import com.linear.service.InboxService;
import com.linear.service.NoticeService;
import com.linear.service.ProblemService;
import com.linear.service.RoleService;
import com.linear.service.ServiceException;
import com.linear.service.TaskService;
import com.linear.service.UserService;
import com.linear.utils.SendMail;
import com.ren.struts.core.Action;
import com.ren.struts.core.RequestAware;
import com.ren.struts.core.ResponseAware;

import cn.ren.utils.CommonUtils;


public class UserAction implements Action, RequestAware,ResponseAware {
	private UserService userService;
	private RoleService roleService;
	
	private ProblemService problemService;
	private HeadLoad headLoad;
	
	public String update() {
		User user = CommonUtils.toBean(req.getParameterMap(), User.class);
		String repassword = req.getParameter("repassword");
		if(user.getPassword() == null || repassword == null ||!user.getPassword().equals(repassword)) {
			req.setAttribute("password_err", "请检查密码填写");
			req.setAttribute("user", user);
			return "infoupdate";
		}
		User my = (User)req.getSession().getAttribute("user");
		my.setPassword(user.getPassword());
		my.setName(user.getName());
		my.setEmail(user.getEmail());
		my.setDescript(user.getDescript());
		my.setHeadpic(user.getHeadpic());
		try {
			userService.update(my);
		} catch (ServiceException e) {
			ErrorsToReqUtil.toReq(e.getErrMap(), req);
			req.setAttribute("user", user);
			return "infoupdate";
		}
		return touserinfo();
	}
	
	public String toupdate() {
		headLoad.loadHead(req);
		req.setAttribute("user", req.getSession().getAttribute("user"));
		return "infoupdate";
	}
	/**
	 * 前往教师注册
	 * @return
	 */
	public String toteachersignup() {
		return "teachersignup";
	}
	
	/**
	 * 前往登陆页面
	 * @return
	 */
	public String tologin() {
		return "login";
	}
	/**
	 * 前往注册页面
	 * @return
	 */
	public String toregist() {
		return "regist";
	}
	/**
	 * 返回主页
	 * @return
	 */
	public String toindex() {
		String pageindex_str = req.getParameter("pageindex");
		String level = req.getParameter("level");
		String issolved = req.getParameter("issolved");
		String user_id = req.getParameter("user_id");
		headLoad.loadHead(req);
		//问题LIST
		PageBean pb = null;
		List<Problem> problemList = null;
		if((level == null || "".equals(level)) &&(issolved == null || "".equals(issolved))&&(user_id==null ||"".equals(user_id))) {
			pb = new PageBean(problemService.getProblemNum(),pageindex_str);
			problemList = problemService.getList(pb.getLimit_start(), pb.getLimit_offset());
		}else if(level != null && !"".equals(level)) {
			int temp_level = 0;
			try {
				temp_level = Integer.parseInt(level);
			}catch(Exception e) {
				temp_level = 0;
			}
			req.setAttribute("level", level);
			pb = new PageBean(problemService.getProblemNumOfLevel(temp_level), pageindex_str);
			problemList = problemService.getListOfLevel(temp_level,pb.getLimit_start(), pb.getLimit_offset());
		}else if(issolved != null && !"".equals(issolved)){
			int temp_iss = 0;
			try {
				temp_iss = Integer.parseInt(issolved);
			}catch(Exception e) {
				temp_iss = 0;
			}
			req.setAttribute("issolved", issolved);
			pb = new PageBean(problemService.getProblemNumOfIssolved(temp_iss), pageindex_str);
			problemList = problemService.getListOfIssolved(temp_iss,pb.getLimit_start(), pb.getLimit_offset());
		}else if(user_id != null && !"".equals(user_id)) {
			int t_uid= 0;
			try {
				t_uid = Integer.parseInt(user_id);
			}catch(Exception e) {
				t_uid = 0;
			}
			req.setAttribute("user_id", user_id);
			pb = new PageBean(problemService.getProblemNumByUser_id(t_uid), pageindex_str);
			problemList = problemService.getListByUser_id(t_uid,pb.getLimit_start(), pb.getLimit_offset());
		}
		req.setAttribute("problemList", problemList);
		req.setAttribute("pagebean", pb);
		return "index";
	}
	public String touserinfo() {
		headLoad.loadHead(req);
		return "userinfo";
	}
	/**
	 * 登出
	 * @return
	 */
	public String logout() {
		req.getSession().removeAttribute("user");
		req.getSession().invalidate();
		return "login";
	}
	/**
	 * 用户登陆
	 * @return
	 */
	public String login() {
		String res = "login";
		User user = null;
		String user_no = "";
		String ps = "";
		try {
			user_no = req.getParameter("user_no");
			ps = req.getParameter("password");
			user = userService.login(user_no, ps);
			//登陆成功
			req.getSession().setAttribute("user", user);
			
			res = toindex();
		} catch (ServiceException e) {
			//登陆失败
			ErrorsToReqUtil.toReq(e.getErrMap(), req);
			req.setAttribute("user_no", user_no);
			req.setAttribute("password", ps);
			return "login";
		}
		return res;
	}
	/**
	 * 管理员注册审批
	 * @return
	 */
	public String tosignupcheck() {
		headLoad.loadHead(req);
		String pageindex = req.getParameter("pageindex");
		String tg = req.getParameter("tg");
		int tgnum = 0;
		if(tg != null && "1".equals(tg)) {
			tgnum = 1;
			req.setAttribute("tg", "1");
		}
		PageBean pagebean = new PageBean(userService.getTeaNumByTG(tgnum), pageindex);
		List<User> userList = userService.getTeaListByTG(tgnum, pagebean.getLimit_start(),
				pagebean.getLimit_offset());
		req.setAttribute("pagebean", pagebean);
		req.setAttribute("userList", userList);
		return "signupcheck";
	}
	/**
	 * 管理员审批
	 */
	public String signupcheck() {
		String user_id = req.getParameter("user_id");
		try {
			User user = userService.getById(Integer.parseInt(user_id));
			if(user != null) {
				userService.signupcheck(user);//审批通过
			}
		}catch(Exception e) {
			
		}
		return tosignupcheck();
	}
	/**
	 * 审批不通过，审批删除
	 * @return
	 */
	public String signupdelete() {
		String user_id = req.getParameter("user_id");
		try {
			User user = userService.getById(Integer.parseInt(user_id));
			if(user != null) {
				userService.signupdelete(user);
			}
		}catch(Exception e) {
			
		}
		return tosignupcheck();
	}
	/**
	 * 教师注册
	 * @return
	 */
	public String tearegist() {
		User user = CommonUtils.toBean(req.getParameterMap(), User.class);
		String repassword = req.getParameter("repassword");
		
		if(repassword == null || "".equals(repassword) || !repassword.equals(user.getPassword())) {
			req.setAttribute("password_err", "请检查密码输入");
			ErrorsToReqUtil.reqP2A(req);
			return "regist";
		}
		//设置其他值
		user.setActivation(0);
		user.setCtime(new Date());
		user.setRole_id(roleService.getTeacherRole().getPk_id());
		user.setHeadpic("images/i3.png");
		//注册
		try {
			userService.regist(user);
		} catch (ServiceException e) {
			//注册失败！
			ErrorsToReqUtil.toReq(e.getErrMap(), req);
			ErrorsToReqUtil.reqP2A(req);
			return "regist";
		}
		//发送邮件
		int checknum = Math.abs(new Random().nextInt(999999));
		SendMail sendMail = new SendMail();
		sendMail.setTo(user.getEmail());
		sendMail.setBody("[线性代数学习]验证码:" + checknum);
		try {
			sendMail.mailsend();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		//把验证码发送到该页面
		req.setAttribute("truechecknum", checknum);
		//账号ID
		req.setAttribute("checkuserno", user.getUser_no());
		//email
		req.setAttribute("email", user.getEmail());
		return "emailcheck";
	}
	/**
	 * 学生注册
	 * @return
	 */
	public String sturegist() {
		
		User user = CommonUtils.toBean(req.getParameterMap(), User.class);
		String repassword = req.getParameter("repassword");
		
		if(repassword == null || "".equals(repassword) || !repassword.equals(user.getPassword())) {
			req.setAttribute("password_err", "请检查密码输入");
			ErrorsToReqUtil.reqP2A(req);
			return "regist";
		}
		//设置其他值
		user.setActivation(0);
		user.setCtime(new Date());
		user.setRole_id(roleService.getStudentRole().getPk_id());
		user.setHeadpic("images/i3.png");
		//注册
		try {
			userService.regist(user);
		} catch (ServiceException e) {
			//注册失败！
			ErrorsToReqUtil.toReq(e.getErrMap(), req);
			ErrorsToReqUtil.reqP2A(req);
			return "regist";
		}
		//发送邮件
		int checknum = Math.abs(new Random().nextInt(999999));
		SendMail sendMail = new SendMail();
		sendMail.setTo(user.getEmail());
		sendMail.setBody("[线性代数学习]验证码:" + checknum);
		try {
			sendMail.mailsend();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		//把验证码发送到该页面
		req.setAttribute("truechecknum", checknum);
		//账号ID
		req.setAttribute("checkuserno", user.getUser_no());
		//email
		req.setAttribute("email", user.getEmail());
		return "emailcheck";
	}
	/**
	 * 重新发送邮件
	 * AJAX
	 * @return
	 */
	public String resendMail() {
		int checknum = Math.abs(new Random().nextInt(999999));
		SendMail sendMail = new SendMail();
		sendMail.setTo(req.getParameter("email"));
		sendMail.setBody("[线性代数学习]验证码:" + checknum);
		try {
			sendMail.mailsend();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		try {
			res.getWriter().println(checknum);
			res.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 邮件激活提交
	 * @return
	 */
	public String mailcheck() {
		String truechecknum = req.getParameter("truechecknum");
		String mychecknum = req.getParameter("checknum");
		String checkuserno = req.getParameter("checkuserno");
		String res = "";
		if(truechecknum.equals(mychecknum)) {
			//前去激活
			try {
				userService.mailcheck(checkuserno);
				req.setAttribute("_message", "激活成功[如果您申请的是教师账号,请等待管理员最后审批]");
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ServiceException e) {
				req.setAttribute("_message", e.getErrorMessage("mailcheck_err"));
			}
			
			res = "message";
		}else {
			//重新返回到激活页面
			req.setAttribute("truechecknum", truechecknum);
			req.setAttribute("checknum_err", "激活失败!");
			req.setAttribute("checkuserno", checkuserno);
			res = "emailcheck";
		}
		return res;
	}
	
	
	
	
	@Override
	public void requestAware(HttpServletRequest req) {
		this.req = req;
	}
	
	
	@Override
	public String execute() {
		return null;
	}
	private HttpServletRequest req;
	private HttpServletResponse res;
	@Override
	public void responseAware(HttpServletResponse res) {
		this.res = res;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public ProblemService getProblemService() {
		return problemService;
	}

	public void setProblemService(ProblemService problemService) {
		this.problemService = problemService;
	}

	public HeadLoad getHeadLoad() {
		return headLoad;
	}

	public void setHeadLoad(HeadLoad headLoad) {
		this.headLoad = headLoad;
	}
}

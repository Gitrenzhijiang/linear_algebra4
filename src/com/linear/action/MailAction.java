package com.linear.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.linear.domain.Inbox;
import com.linear.domain.PageBean;
import com.linear.domain.User;
import com.linear.service.InboxService;
import com.linear.service.ServiceException;
import com.linear.service.UserService;
import com.ren.struts.core.Action;
import com.ren.struts.core.RequestAware;

import cn.ren.utils.CommonUtils;

public class MailAction implements Action, RequestAware {
	
	public String tomail() {
		headLoad.loadHead(req);
		String pageindex = req.getParameter("pageindex");
		String isread_str = req.getParameter("isread");
		String sended = req.getParameter("sended");//已发送=1
		User my = (User)req.getSession().getAttribute("user");
		PageBean pagebean = null;
		List<Inbox> inboxList = null;
		if(sended != null && "1".equals(sended)) {
			//自己发送的
			pagebean = new PageBean(inboxService.getTotalNumBySendId(my.getPk_id()), pageindex);
			inboxList = inboxService.getListBySendId(my.getPk_id(), 
					pagebean.getLimit_start(),pagebean.getLimit_offset());
			req.setAttribute("sended", sended);
		}else if(isread_str != null && ("1".equals(isread_str) || "0".equals(isread_str))) {
			//接受的已读或者未读
			if("1".equals(isread_str)) {
				pagebean = new PageBean(inboxService.getTotalNumOfReadedByRecv_id(my.getPk_id()), pageindex);
				inboxList = inboxService.getReadedListByRecv_id(my.getPk_id(), 
						pagebean.getLimit_start(),pagebean.getLimit_offset());
			}else {
				pagebean = new PageBean(inboxService.getTotalnumOfUnreadByRecv_id(my.getPk_id()), pageindex);
				inboxList = inboxService.getUnreadListByRecv_id(my.getPk_id(), 
						pagebean.getLimit_start(),pagebean.getLimit_offset());
			}
			req.setAttribute("isread", isread_str);
		}else {//所有自己接受的
			pagebean = new PageBean(inboxService.getTotalNumByRecv_id(my.getPk_id()), pageindex);
			inboxList = inboxService.getListByRecv_id(my.getPk_id(), 
					pagebean.getLimit_start(),pagebean.getLimit_offset());
		}
		req.setAttribute("pagebean", pagebean);
		req.setAttribute("inboxList", inboxList);
		return "mail";
	}

	
	public String add() {
		String user_no = req.getParameter("user_no");//邮件接收人
		Inbox inbox = CommonUtils.toBean(req.getParameterMap(), Inbox.class);
		if(user_no == null || "".equals(user_no)) {
			req.setAttribute("form_err", "邮件接收人为空");
			return toadd();
		}
		
		try {
			User u = userService.getByNo(user_no);
			if(u == null) {
				throw new RuntimeException("邮件接受人不存在");
			}
			User my = (User)req.getSession().getAttribute("user");
			inbox.setReceive_id(u.getPk_id());
			inbox.setSend_id(my.getPk_id());
			inbox.setIsread(0);
			inbox.setSendtime(new Date());
			inboxService.add(inbox);
		} catch (Exception e) {
			req.setAttribute("form_err", e.getMessage());
			req.setAttribute("user_no", user_no);
			req.setAttribute("inbox", inbox);
			return toadd();
		}
		
		return tomail();
	}
	
	public String toadd() {
		headLoad.loadHead(req);
		
		return "mailadd";
	}
	/**
	 * 回复
	 * @return
	 */
	public String reply() {
		String user_id = req.getParameter("user_id");
		if(user_id != null && !"".equals(user_id)) {
			try {
				req.setAttribute("user_no", userService.getById(Integer.parseInt(user_id)).getUser_no());
			}catch(Exception e) {
				
			}
		}
		return toadd();
	}
	/**
	 * 设为已读
	 * @return
	 */
	public String setread() {
		String inbox_id = req.getParameter("inbox_id");
		try {
			inboxService.setAReadByInbox_id(Integer.parseInt(inbox_id));
		}catch(Exception e) {
			
		}
		return tomail();
	}
	public String delete() {
		String inbox_id = req.getParameter("inbox_id");
		try {
			inboxService.deleteByInbox_Id(Integer.parseInt(inbox_id));
		}catch(Exception e) {
			
		}
		return tomail();
	}
	private InboxService inboxService;
	private UserService userService;
	private HeadLoad headLoad;
	HttpServletRequest req;
	@Override
	public void requestAware(HttpServletRequest req) {
		this.req = req;
	}
	@Override
	public String execute() {
		return null;
	}


	public InboxService getInboxService() {
		return inboxService;
	}


	public void setInboxService(InboxService inboxService) {
		this.inboxService = inboxService;
	}


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public HeadLoad getHeadLoad() {
		return headLoad;
	}


	public void setHeadLoad(HeadLoad headLoad) {
		this.headLoad = headLoad;
	}

}

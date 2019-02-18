package com.linear.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.linear.domain.Notice;
import com.linear.domain.PageBean;
import com.linear.domain.User;
import com.linear.service.NoticeService;
import com.linear.service.ServiceException;
import com.ren.struts.core.Action;
import com.ren.struts.core.RequestAware;

import cn.ren.utils.CommonUtils;

public class NoticeAction implements Action, RequestAware {
	
	public String already() {
		String noticeid = req.getParameter("notice_id");
		int nid = 0;
		try {
			nid = Integer.parseInt(noticeid);
			User my = (User)req.getSession().getAttribute("user");
			noticeService.alreadyByNis_id(noticeService.getNisByNidAndUser_id(nid,my.getPk_id()).getPk_id());
		}catch(Exception e) {
			
		}
		return toallnotice();
	}
	public String delete() {
		String noticeid = req.getParameter("notice_id");
		int nid = 0;
		try {
			nid = Integer.parseInt(noticeid);
			noticeService.deleteByNotice_id(nid);
		}catch(Exception e) {
			return tomynotice();
		}
		return tomynotice();
	}
	/**
	 * 跳转到我的通知
	 * @return
	 */
	public String tomynotice() {
		String pageindex = req.getParameter("pageindex");
		//头信息
		headLoad.loadHead(req);
		User my = (User)req.getSession().getAttribute("user");
		PageBean pagebean = new PageBean(noticeService.getTotalSendNoticeByUser_id(my.getPk_id()),
				pageindex);
		List<Notice> noticeList = noticeService.getSendListByUser_id( my.getPk_id(),
				pagebean.getLimit_start(), pagebean.getLimit_offset());
		req.setAttribute("pagebean", pagebean);
		req.setAttribute("noticeList", noticeList);
		return "noticedelete";
	}
	public String toaddnotice() {
		return "noticeadd";
	}
	public String addnotice() {
		User my = (User)req.getSession().getAttribute("user");
		Notice notice = CommonUtils.toBean(req.getParameterMap(), Notice.class);
		notice.setCreatetime(new Date());
		notice.setCreateuser_id(my.getPk_id());
		try {
			noticeService.add(notice);
			
		} catch (ServiceException e) {
			req.setAttribute("form_err", e.getMessage());
			return "noticeadd";
		}
		return "/notice.action!tomynotice";
	}
	/**
	 * 到所有通知
	 * @return
	 */
	public String toallnotice() {
		String pageindex = req.getParameter("pageindex");
		//头信息
		headLoad.loadHead(req);
		//加载自己所有未读通知
		User my = (User)req.getSession().getAttribute("user");
		List<Notice> unreadNotices = noticeService.getListOfUnread(my.getPk_id(),
				0, noticeService.getTotalNumOfUnreadByUser_id(my.getPk_id()));
		req.setAttribute("unreadNotices", unreadNotices);
		//记载所有通知
		PageBean pagebean = new PageBean(noticeService.getTotalNumByUser_id(my.getPk_id()),
				pageindex);
		List<Notice> allNoticeList = noticeService.getListByUser_id(my.getPk_id(),
				pagebean.getLimit_start(), pagebean.getLimit_offset());
		req.setAttribute("pagebean", pagebean);
		req.setAttribute("allNoticeList", allNoticeList);
		return "allnotice";
	}
	
	
	private NoticeService noticeService;
	private HeadLoad headLoad;
	
	@Override
	public void requestAware(HttpServletRequest req) {
		this.req = req;
	}
	private HttpServletRequest req;
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}
	public NoticeService getNoticeService() {
		return noticeService;
	}
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	public HeadLoad getHeadLoad() {
		return headLoad;
	}
	public void setHeadLoad(HeadLoad headLoad) {
		this.headLoad = headLoad;
	}

}

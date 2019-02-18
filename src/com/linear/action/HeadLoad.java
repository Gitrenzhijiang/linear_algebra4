package com.linear.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.linear.domain.Inbox;
import com.linear.domain.Notice;
import com.linear.domain.Task;
import com.linear.domain.User;
import com.linear.service.InboxService;
import com.linear.service.NoticeService;
import com.linear.service.TaskService;

public class HeadLoad {
	private InboxService inboxService;
	private NoticeService noticeService;
	private TaskService taskService;
	
	public void loadHead(HttpServletRequest req) {
		User my = (User) req.getSession().getAttribute("user");
		//加载该用户未读的3个邮件
		List<Inbox> threeInboxs = inboxService.getUnreadListByRecv_id(my.getPk_id(), 0, 3);
		req.setAttribute("threeInboxs", threeInboxs);
		req.setAttribute("totalunreadinboxnum", inboxService.getTotalnumOfUnreadByRecv_id(my.getPk_id()));
		//加载该用户未读的3个通知
		List<Notice> threeNotices = noticeService.getListOfUnread(my.getPk_id(), 0, 3);
		req.setAttribute("threeNotices", threeNotices);
		req.setAttribute("totalunreadnoticenum", noticeService.getTotalNumOfUnreadByUser_id(my.getPk_id()));
		//加载该用户正在进行的任务
		List<Task> threeTasks = taskService.getListUnfinishByUser_id(my.getPk_id(), 0, 3);
		req.setAttribute("threeTasks", threeTasks);
		req.setAttribute("totalunfinishtasknum", taskService.getTotalNumOfUnfinishByUser_id(my.getPk_id()));
	}

	public InboxService getInboxService() {
		return inboxService;
	}

	public void setInboxService(InboxService inboxService) {
		this.inboxService = inboxService;
	}

	public NoticeService getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
}

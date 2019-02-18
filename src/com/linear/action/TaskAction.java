package com.linear.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.linear.domain.PageBean;
import com.linear.domain.Task;
import com.linear.domain.Taskfinish;
import com.linear.domain.User;
import com.linear.service.TaskService;
import com.linear.utils.DownLoadUtil;
import com.linear.utils.UploadUtil;
import com.ren.struts.core.Action;
import com.ren.struts.core.RequestAware;
import com.ren.struts.core.ResponseAware;

import cn.ren.utils.CommonUtils;

public class TaskAction implements Action, RequestAware, ResponseAware {
	/**
	 * 学生跳转到评价详情页面
	 * @return
	 */
	public String remarkdesc() {
		headLoad.loadHead(req);
		String task_idstr = req.getParameter("task_id");
		User my = (User)req.getSession().getAttribute("user");
		try {
			int task_id = Integer.parseInt(task_idstr);
			Taskfinish taskfinish = taskService.getTaskfinishByUser_idAndTask_id(my.getPk_id(), task_id);
			if(taskfinish == null) {
				throw new RuntimeException();
			}
			req.setAttribute("taskfinish", taskfinish);
			return "remarkdesc";
		}catch(Exception e) {
			return "errorpage";
		}
	}
	
	
	public String remarktf() {
		headLoad.loadHead(req);
		Taskfinish tf = CommonUtils.toBean(req.getParameterMap(), Taskfinish.class);
		Taskfinish tft = null;
		try {
			tft = taskService.getTFById(tf.getPk_id());
			if(tft == null || tf == null)
				throw new RuntimeException("不存在的任务提交");
			tft.setRemark(tf.getRemark());
			User user = (User)req.getSession().getAttribute("user");
			tft.setRemarkuser_id(user.getPk_id());
			taskService.updateremark(tft);
		}catch(Exception e) {
			req.setAttribute("form_err", e.getMessage());
			req.setAttribute("taskfinish", tf);
			return "remarktf";
		}
		return "/task.action!totaskfinish?task_id=" + tft.getTask().getPk_id();
	}
	
	public String toremarktf() {
		String tf_id = req.getParameter("tf_id");
		try {
			Taskfinish tf = taskService.getTFById(Integer.parseInt(tf_id));
			if(tf != null) {
				
				req.setAttribute("taskfinish", tf);
				return "remarktf";
			}
		}catch(Exception e) {
			return "errorpage";
		}
		return "errorpage";
	}
	/**
	 * 下载作业提交附件
	 * @return
	 */
	public String downloadTF() {
		String tfid = req.getParameter("tf_id");
		try {
			Taskfinish tf = taskService.getTFById(Integer.parseInt(tfid));
			if(tf != null && tf.getFilepath()!= null) {
				DownLoadUtil.download(tf.getFilepath(), req, res);
			}
		}catch(Exception e) {
			return null;
		}
		return null;
	}
	
	
	
	public String totaskfinish() {
		headLoad.loadHead(req);
		String tid = req.getParameter("task_id");
		String pageindex = req.getParameter("pageindex");
		//加载关于这个任务的所有任务完成情况
		try {
			int task_id = Integer.parseInt(tid); 
			if(taskService.getById(task_id) == null) {
				throw new RuntimeException();
			}
			
			PageBean pagebean = new PageBean(taskService.getTotalNumByTask_id(task_id), 
					pageindex, 5);
			List<Taskfinish> tfList = taskService.getTFListByTask_id(task_id, 
					pagebean.getLimit_start(),pagebean.getLimit_offset());
			req.setAttribute("pagebean", pagebean);
			req.setAttribute("tfList", tfList);
			req.setAttribute("task_id", tid);
		}catch(Exception e) {
			return "errorpage";
		}
		return "taskfinish";
	}
	
	
	public String tolist2finish() {
		headLoad.loadHead(req);
		String pageindex = req.getParameter("pageindex");
		//加载所有作业
		PageBean pagebean = new PageBean(taskService.getTotalNum(), pageindex,5);
		List<Task> taskList = taskService.getList(pagebean.getLimit_start(),
				pagebean.getLimit_offset());
		req.setAttribute("pagebean", pagebean);
		req.setAttribute("taskList", taskList);
		return "list2finish";
	}
	
	/**
	 * 跳转到我的任务页面
	 * @return
	 */
	public String tomytask() {
		headLoad.loadHead(req);
		String pageindex = req.getParameter("pageindex");
		//加载未完成作业
		User my = (User)req.getSession().getAttribute("user");
		List<Task> utaskList = taskService.getListUnfinishByUser_id(my.getPk_id(),0,
				taskService.getTotalNumOfUnfinishByUser_id(my.getPk_id()));
		//已完成的作业
		PageBean pagebean = new PageBean(taskService.getTotalNumOfFinishedByUser_id(my.getPk_id()), pageindex);
		List<Task> taskList = taskService.getListFinishedByUser_id(my.getPk_id(),
				pagebean.getLimit_start(),pagebean.getLimit_offset());
		req.setAttribute("utaskList", utaskList);
		req.setAttribute("pagebean", pagebean);
		req.setAttribute("taskList", taskList);
		return "mytask";
	}
	/**
	 * 跳转到任务详情界面
	 * @return
	 */
	public String taskdesc() {
		headLoad.loadHead(req);
		String tid = req.getParameter("task_id");
		try {
			Task task = taskService.getById(Integer.parseInt(tid));
			if(task == null)
				throw new RuntimeException();
			req.setAttribute("task", task);
		}catch(Exception e) {
			//出错页面
			return "errorpage";
		}
		return "taskdesc";
	}
	/**
	 * 跳转到提交作业页面
	 * @return
	 */
	public String tosubmit() {
		headLoad.loadHead(req);
		String tid = req.getParameter("task_id");
		try {
			Task task = taskService.getById(Integer.parseInt(tid));
			if(task == null)
				throw new RuntimeException();
			req.setAttribute("task", task);
		}catch(Exception e) {
			//出错页面
			return "errorpage";
		}
		return "submittask";
	}
	public String submit() {
		Taskfinish taskfinish = new Taskfinish();
		UploadUtil uploadUtil = null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(factory);
			
			List<FileItem> fileItemList = sfu.parseRequest(req);
			//临时变量
			FileItem ffi = null;
			for(FileItem fileItem : fileItemList) {
				if(fileItem.isFormField()) {//如果是普通表单项目
					String value = fileItem.getString("utf-8");
					if("res".equals(fileItem.getFieldName()))
						taskfinish.setRes(value);
					else if("task_id".equals(fileItem.getFieldName())) {
						taskfinish.setTask_id(Integer.parseInt(value.trim()));
					}
				}else {
					ffi = fileItem;
				}
			}
			User my = (User)req.getSession().getAttribute("user");
			taskfinish.setFinishtime(new Date());
			taskfinish.setStatus(1);
			taskfinish.setUser_id(my.getPk_id());
			Taskfinish tf = taskService.getTaskfinishByUser_idAndTask_id(my.getPk_id(),taskfinish.getTask_id());
			taskfinish.setTask(tf.getTask());
			taskfinish.setPk_id(tf.getPk_id());
			
			if(ffi != null) {
				if(ffi.getSize() > 1024 * 1024 * 10) {
					throw new RuntimeException("上传文件大小超过10M");
				}
				uploadUtil = new UploadUtil("files", ffi, req);
				taskfinish.setFilepath(uploadUtil.upload());
			}
			taskService.submitTask(taskfinish);
		}catch(Exception e) {
			e.printStackTrace();
			if(uploadUtil != null)
				uploadUtil.delete();
			req.setAttribute("form_err", e.getMessage());
			req.setAttribute("taskfinish", taskfinish);
			return "submittask";
		}
		return "/task.action!tomytask";
	}
	public String download() {
		String tid = req.getParameter("task_id");
		try {
			Task task = taskService.getById(Integer.parseInt(tid));
			if(task != null && task.getFilepath() != null) {
				DownLoadUtil.download(task.getFilepath(), req, res);
			}
		}catch(Exception e) {
			return null;
		}
		return null;
	}
	/**
	 * 添加任务
	 * @return
	 */
	public String add() {
		Task task = new Task();
		UploadUtil uploadUtil = null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(factory);
			
			List<FileItem> fileItemList = sfu.parseRequest(req);
			for(FileItem fileItem : fileItemList) {
				if(fileItem.isFormField()) {//如果是普通表单项目
					String value = fileItem.getString("utf-8");
					if("name".equals(fileItem.getFieldName()))
						task.setName(value);
					else if("descript".equals(fileItem.getFieldName())) {
						task.setDescript(value);
					}
				}else {
					//限制上传大小
					if(fileItem.getSize() > 1024 * 1024 * 3) {
						throw new RuntimeException("上传文件超过3M");
					}
					uploadUtil = new UploadUtil("files", fileItem, req);
					task.setFilepath(uploadUtil.upload());
				}
			}
			User my = (User)req.getSession().getAttribute("user");
			task.setCreatetime(new Date());
			task.setTeacher_id(my.getPk_id());
			taskService.add(task);
		}catch(Exception e) {
			if(uploadUtil != null)
				uploadUtil.delete();
			req.setAttribute("form_err", e.getMessage());
			req.setAttribute("task", task);
			return toadd();
		}
		return "/task.action!tolist2finish";
	}
	
	
	
	
	public String toadd() {
		headLoad.loadHead(req);
		return "taskadd";
	}



	private HeadLoad headLoad;
	private TaskService taskService;
	@Override
	public void responseAware(HttpServletResponse res) {
		this.res = res;
	}
	@Override
	public void requestAware(HttpServletRequest req) {
		this.req = req;
	}
	private HttpServletRequest req;
	private HttpServletResponse res;
	@Override
	public String execute() {
		return null;
	}


	public TaskService getTaskService() {
		return taskService;
	}


	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}


	public HeadLoad getHeadLoad() {
		return headLoad;
	}


	public void setHeadLoad(HeadLoad headLoad) {
		this.headLoad = headLoad;
	}

}

package com.linear.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.linear.domain.Answer;
import com.linear.domain.PageBean;
import com.linear.domain.Problem;
import com.linear.domain.User;
import com.linear.service.AnswerService;
import com.linear.service.ProblemService;
import com.linear.utils.UploadUtil;
import com.ren.struts.core.Action;
import com.ren.struts.core.RequestAware;

public class QuestionAction implements Action, RequestAware {
	/**
	 * 设置已解决
	 * @return
	 */
	public String setissolved() {
		String problem_id = req.getParameter("problem_id");
		try {
			int pid = Integer.parseInt(problem_id);
			problemService.setissolved(pid);
		}catch(Exception e) {
			
		}
		return "/question.action!toanswerdiscuss?problem_id="+ problem_id;
	}
	
	/**
	 * 设置问题难度(管理员)
	 * @return
	 */
	public String setlevel() {
		String level = req.getParameter("level");
		String problem_id = req.getParameter("problem_id");
		try {
			int int_level = Integer.parseInt(level);
			int int_pid = Integer.parseInt(problem_id);
			problemService.setlevel(int_pid, int_level);
		}catch(Exception e) {
			
		}
		return "/question.action!toanswerdiscuss?problem_id="+ problem_id;
	}
	
	/**
	 * 提交问题
	 * @return
	 */
	public String ask() {
		//上传文件
		Problem problem = new Problem();
		UploadUtil upl = null;
		String res = "ask";
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(factory);
			
			List<FileItem> fileItemList = sfu.parseRequest(req);
			for(FileItem fileItem : fileItemList) {
				if(fileItem.isFormField()) {
					String fieldname = fileItem.getFieldName();
					String value = fileItem.getString("utf-8");
					if("title".equals(fieldname)) {
						problem.setTitle(value);
					}else if("descript".equals(fieldname)) {
						problem.setDescript(value);
					}
				}else {
					//文件
					if(fileItem.getSize() > 1024 * 1024) {
						throw new RuntimeException("上传图片过大");
					}
					upl = new UploadUtil("files", fileItem, req);
					problem.setQpicpath(upl.upload());
				}
			}
			User my = (User)req.getSession().getAttribute("user");
			problem.setCreatetime(new Date());
			problem.setIssolved(0);
			problem.setLevel(0);
			problem.setUser_id(my.getPk_id());
			problemService.add(problem);
			
			res = "/user.action!toindex";
		}catch(Exception e) {
			if(upl!=null)
				upl.delete();
			req.setAttribute("form_err", e.getMessage());
			return toask();
		}
		return res;
	}
	/**
	 * 跳转到提问页面
	 * @return
	 */
	public String toask() {
		headLoad.loadHead(req);
		return "ask";
	}
	/**
	 * 跳转到回答问题
	 * @return
	 */
	public String toanswerquestion() {
		//加载头信息
		headLoad.loadHead(req);
		String problem_id = req.getParameter("problem_id");
		int pid = 0;
		try {
			pid = Integer.parseInt(problem_id);
		}catch(Exception e) {
			return "errorpage";
		}
		Problem problem = problemService.getById(pid);
		if(problem == null) {
			return "errorpage";
		}
		req.setAttribute("problem", problem);
		return "answerquestion";
	}
	
	/**
	 * 讨论/查看所有回答
	 * @return
	 */
	public String toanswerdiscuss() {
		//加载头信息
		headLoad.loadHead(req);
		
		String problem_id = req.getParameter("problem_id");
		String pageindex = req.getParameter("pageindex");
		int pid = 0;
		try {
			pid = Integer.parseInt(problem_id);
		}catch(Exception e) {
			return "errorpage";
		}
		Problem problem = problemService.getById(pid);
		if(problem == null) {
			return "errorpage";
		}
		//问题不等于null,加载所有的回答
		PageBean pb = new PageBean(answerService.getTotalAnswerNumOfProblemId(pid), pageindex);
		List<Answer> answerList = answerService.getListByProblem_id(pid, pb.getLimit_start(), pb.getLimit_offset());
		req.setAttribute("user", req.getSession().getAttribute("user"));
		req.setAttribute("problem", problem);
		req.setAttribute("pagebean", pb);
		req.setAttribute("answerList", answerList);
		return "answerdiscuss";
	}
	
	
	private ProblemService problemService;
	private AnswerService answerService;
	private HeadLoad headLoad;
	@Override
	public void requestAware(HttpServletRequest req) {
		this.req = req;
	}
	@Override
	public String execute() {
		return null;
	}
	private HttpServletRequest req;
	public ProblemService getProblemService() {
		return problemService;
	}

	public void setProblemService(ProblemService problemService) {
		this.problemService = problemService;
	}

	public AnswerService getAnswerService() {
		return answerService;
	}

	public void setAnswerService(AnswerService answerService) {
		this.answerService = answerService;
	}

	public HeadLoad getHeadLoad() {
		return headLoad;
	}

	public void setHeadLoad(HeadLoad headLoad) {
		this.headLoad = headLoad;
	}
}

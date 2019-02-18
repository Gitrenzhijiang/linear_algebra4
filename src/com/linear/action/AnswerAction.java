package com.linear.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.linear.domain.Answer;
import com.linear.domain.Discuss;
import com.linear.domain.User;
import com.linear.service.AnswerService;
import com.linear.service.ProblemService;
import com.linear.service.ServiceException;
import com.linear.utils.DateFormat;
import com.linear.utils.UploadUtil;
import com.ren.struts.core.Action;
import com.ren.struts.core.RequestAware;
import com.ren.struts.core.ResponseAware;

import cn.ren.utils.CommonUtils;

public class AnswerAction implements RequestAware, Action, ResponseAware{
	/**
	 * 回答问题
	 * @return
	 */
	public String answer() {
		//解析上传的东西
		Answer answer = new Answer();
		String res = "";
		UploadUtil uploadUtil = null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(factory);
			
			List<FileItem> fileItemList = sfu.parseRequest(req);
			for(FileItem fileItem : fileItemList) {
				if(fileItem.isFormField()) {//如果是普通表单项目
					String value = fileItem.getString("utf-8");
					if(fileItem.getFieldName().equals("answertext"))
						answer.setAnswertext(value);
					else if(fileItem.getFieldName().equals("problem_id")) {
						answer.setProblem_id(Integer.parseInt(value));
					}
				}else {
					//限制上传大小
					if(fileItem.getSize() > 1024 * 1024) {
						throw new RuntimeException("上传文件超过1M");
					}
					uploadUtil = new UploadUtil("files", fileItem, req);
					answer.setApicpath(uploadUtil.upload());
				}
			}
			
			//一切ok之后,写入数据库
			User my = (User)req.getSession().getAttribute("user");
			answer.setUser_id(my.getPk_id());
			answer.setAnswertime(new Date());
			
			answerService.addAnswer(answer);
			//返回所有回答页面
			res = "/question.action!toanswerdiscuss?problem_id=" + answer.getProblem_id();
		}catch(Exception e) {
			if(uploadUtil != null)
				uploadUtil.delete();
			req.setAttribute("problem", problemService.getById(answer.getProblem_id()));
			req.setAttribute("answer", answer);
			req.setAttribute("form_err", e.getMessage());
			return "answerquestion";
		}
		return res;
	}

	/**
	 * 点赞或踩蛋
	 * @return
	 */
	public String clickokorerr() {
		String answer_id = req.getParameter("answer_id");
		String isgood = req.getParameter("isgood");
		if(answer_id == null || "".equals(answer_id) || isgood==null || "".equals(isgood)) {
			return  null;
		}
		int a_id = 0;
		int is_good = 0;
		try {
			a_id  = Integer.parseInt(answer_id);
			is_good = Integer.parseInt(isgood);
			if(is_good != 0 && is_good != 1)
				throw new RuntimeException();
		}catch(Exception e) {
			return null;
		}
		int myid = ((User)req.getSession().getAttribute("user")).getPk_id();
		try {
			answerService.clickOfisgoodToAnswer(a_id, myid, is_good);
		} catch (ServiceException e1) {
			return null;
		}
		//得到关于这个回答的oknum 和 errnum
		int oknum = answerService.getOknumByAnswer_id(a_id);
		int errnum = answerService.getErrnumByAnswer_id(a_id);
		try {
			res.getWriter().println("{oknum:'"+oknum+"',errnum:'"+errnum+"'}");
			res.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 给某个回答评论
	 * @return
	 */
	public String discuss() {
		String time = req.getParameter("time");
		Discuss discuss = CommonUtils.toBean(req.getParameterMap(), Discuss.class);
		try {
			long ltime = Long.parseLong(time);
			discuss.setSpoketime(new Date(ltime));
		}catch(Exception e) {
			discuss.setSpoketime(new Date());
		}
		User my = (User)req.getSession().getAttribute("user");
		discuss.setSpokesman_id(my.getPk_id());
		try {
			answerService.addDisscuss(discuss);
			res.getWriter().println("{name:'" +my.getName()+ "',time:'"+DateFormat.toString(discuss.getSpoketime())+"'}");
			res.flushBuffer();
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
	
	private AnswerService answerService;
	private ProblemService problemService;
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
	@Override
	public void responseAware(HttpServletResponse res) {
		this.res = res;
	}

	public AnswerService getAnswerService() {
		return answerService;
	}

	public void setAnswerService(AnswerService answerService) {
		this.answerService = answerService;
	}

	public ProblemService getProblemService() {
		return problemService;
	}

	public void setProblemService(ProblemService problemService) {
		this.problemService = problemService;
	}
}

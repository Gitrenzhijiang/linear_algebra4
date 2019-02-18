package com.linear.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.linear.domain.PageBean;
import com.linear.domain.Teachresource;
import com.linear.domain.User;
import com.linear.service.TeachresourceService;
import com.linear.utils.DownLoadUtil;
import com.linear.utils.UploadUtil;
import com.ren.struts.core.Action;
import com.ren.struts.core.RequestAware;
import com.ren.struts.core.ResponseAware;

public class TeachResourceAction implements Action, RequestAware, ResponseAware{
	
	public String delete() {
		String id = req.getParameter("tres_id");
		try {
			Teachresource tr = teachresourceService.getById(Integer.parseInt(id));
			if(tr != null && tr.getRfilepath()!=null) {
				File file = DownLoadUtil.toFile(tr.getRfilepath(), req);
				if(file!=null && file.exists()) {
					file.delete();
				}
				teachresourceService.deleteById(tr.getPk_id());
			}
		}catch(Exception e) {
			
		}
		return todelete();
	}
	
	public String todelete() {
		headLoad.loadHead(req);
		String pageindex = req.getParameter("pageindex");
		User my = (User)req.getSession().getAttribute("user");
		PageBean pagebean = new PageBean(teachresourceService.getTotalNumByUpload_id(my.getPk_id()), pageindex);
		List<Teachresource> trList = teachresourceService.getListByUpload_id(my.getPk_id(),pagebean.getLimit_start(),
				pagebean.getLimit_offset());
		
		req.setAttribute("pagebean", pagebean);
		req.setAttribute("trList", trList);
		return "teachresourcedelete";
	}
	/**
	 * 下载资源文件页面
	 * @return
	 */
	public String toteachresource() {
		//头加载
		headLoad.loadHead(req);
		String pageindex = req.getParameter("pageindex");
		PageBean pagebean = new PageBean(teachresourceService.getTotalNum(), pageindex);
		List<Teachresource> trList = teachresourceService.getList(pagebean.getLimit_start(),
				pagebean.getLimit_offset());
		
		req.setAttribute("pagebean", pagebean);
		req.setAttribute("trList", trList);
		return "teachresource";
	}
	/**
	 * 跳转到上传页面
	 * @return
	 */
	public String toupload() {
		headLoad.loadHead(req);
		return "teachresourceupload";
	}
	/**
	 * 上传
	 * @return
	 */
	public String upload() {
		//上传文件
		Teachresource ts = new Teachresource();
		UploadUtil upl = null;
		String res = "";
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(factory);
			
			List<FileItem> fileItemList = sfu.parseRequest(req);
			for(FileItem fileItem : fileItemList) {
				if(fileItem.isFormField()) {
					String fieldname = fileItem.getFieldName();
					String value = fileItem.getString("utf-8");
					if("rname".equals(fieldname)) {
						ts.setRname(value);
					}else if("descript".equals(fieldname)) {
						ts.setDescript(value);
					}
				}else {
					//文件
					if(fileItem.getSize() > 1024 * 1024 * 20) {
						throw new RuntimeException("上传资源文件过大");
					}
					upl = new UploadUtil("files", fileItem, req);
					ts.setRfilepath(upl.upload());
				}
			}
			User my = (User)req.getSession().getAttribute("user");
			ts.setUpload_id(my.getPk_id());
			ts.setUploadtime(new Date());
			
			teachresourceService.add(ts);
			
			res = toteachresource();
		}catch(Exception e) {
			if(upl!=null)
				upl.delete();
			req.setAttribute("form_err", e.getMessage());
			req.setAttribute("teachresource", ts);
			return toupload();
		}
		return res;
	}
	
	/**
	 * 下载
	 * @return
	 */
	public String download() {
		String id = req.getParameter("tres_id");
		try {
			Teachresource tres = teachresourceService.getById(Integer.parseInt(id));
			if(tres != null && tres.getRfilepath() != null && !"".equals(tres.getRfilepath()))
				DownLoadUtil.download(tres.getRfilepath(), req, res);
		}catch(Exception e) {
			
		}
		return null;
	}
	
	private HeadLoad headLoad;
	private TeachresourceService teachresourceService;
	@Override
	public void requestAware(HttpServletRequest req) {
		this.req = req;
	}
	private HttpServletResponse res;
	private HttpServletRequest req;
	@Override
	public String execute() {
		return null;
	}
	@Override
	public void responseAware(HttpServletResponse res) {
		this.res = res;
	}

	public TeachresourceService getTeachresourceService() {
		return teachresourceService;
	}

	public void setTeachresourceService(TeachresourceService teachresourceService) {
		this.teachresourceService = teachresourceService;
	}

	public HeadLoad getHeadLoad() {
		return headLoad;
	}

	public void setHeadLoad(HeadLoad headLoad) {
		this.headLoad = headLoad;
	}

}

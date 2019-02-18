package com.linear.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.linear.domain.Agorithm;
import com.linear.domain.PageBean;
import com.linear.domain.User;
import com.linear.service.AgorithmService;
import com.linear.utils.DownLoadUtil;
import com.linear.utils.UploadUtil;
import com.ren.struts.core.Action;
import com.ren.struts.core.RequestAware;
import com.ren.struts.core.ResponseAware;

public class AgorithmAction implements Action, RequestAware ,ResponseAware{
	
	
	public String toadd() {
		headLoad.loadHead(req);
		return "agorithmadd";
	}
	public String delete() {
		String agorithm_id = req.getParameter("agorithm_id");
		try {
			int ago_id = Integer.parseInt(agorithm_id);
			//删除文件
			File file = DownLoadUtil.toFile(agorithmService.getById(ago_id).getFilepath(), req);
			if(file.exists()) {
				file.delete();
			}
			agorithmService.deleteById(ago_id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return tomyago();
	}
	/**
	 * 下载
	 * @return
	 */
	public String download() {
		String agorithm_id = req.getParameter("agorithm_id");
		try {
			Agorithm ago = agorithmService.getById(Integer.parseInt(agorithm_id));
			if(ago != null && ago.getFilepath()!=null) {
				//下载
				DownLoadUtil.download(ago.getFilepath(), req, res);
			}
		}catch(Exception e) {
		}
		
		return null;
	}
	/**
	 * 添加
	 * @return
	 */
	public String add() {
		Agorithm ago = new Agorithm();
		UploadUtil uploadUtil = null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload sfu = new ServletFileUpload(factory);
			
			List<FileItem> fileItemList = sfu.parseRequest(req);
			for(FileItem fileItem : fileItemList) {
				if(fileItem.isFormField()) {//如果是普通表单项目
					String value = fileItem.getString("utf-8");
					if(fileItem.getFieldName().equals("name"))
						ago.setName(value);
				}else {
					//限制上传大小
					if(fileItem.getSize() > 1024 * 1024) {
						throw new RuntimeException("上传文件超过1M");
					}
					uploadUtil = new UploadUtil("files", fileItem, req);
					ago.setFilepath(uploadUtil.upload());
				}
			}
			User my = (User)req.getSession().getAttribute("user");
			ago.setUser_id(my.getPk_id());
			agorithmService.add(ago);
		}catch(Exception e) {
			req.setAttribute("form_err", e.getMessage());
			return toadd();
		}
		return "/agorithm.action!tomyago";
	}
	/**
	 * 算法详情
	 * @return
	 */
	public String toagodesc() {
		headLoad.loadHead(req);
		String  agoid = req.getParameter("agorithm_id");
		String charset = req.getParameter("charset");
		Agorithm agorithm = null;
		try {
			agorithm = agorithmService.getById(Integer.parseInt(agoid));
			if(agorithm == null) {
				throw  new  RuntimeException("该算法不存在");
			}
			File file = DownLoadUtil.toFile(agorithm.getFilepath(), req);
			if(file == null || !file.exists()) {
				throw new RuntimeException("算法文件丢失");
			}
			BufferedReader br = null;
			try {
				//编码
				if(charset == null) {
					charset = "UTF-8";
				}
				br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
				String str = null;
				StringBuffer sb = new StringBuffer();
				while((str = br.readLine()) != null) {
					sb.append(str);
					sb.append(System.lineSeparator());
				}
				req.setAttribute("java", sb.toString());
				req.setAttribute("name", agorithm.getName());
			}finally {
				if(br!=null)
					br.close();
			}
			
		}catch(Exception e) {
			req.setAttribute("_message", e.getMessage());
			return "message";
		}
		return "agodesc";
	}
	/**
	 * 跳转到我的算法list
	 * @return
	 */
	public String tomyago() {
		headLoad.loadHead(req);
		String pageindex = req.getParameter("pageindex");
		User my = (User)req.getSession().getAttribute("user");
		PageBean pagebean = new PageBean(agorithmService.getTotalNumByUser_id(my.getPk_id()), pageindex);
		List<Agorithm> agoList = agorithmService.getListByUser_id(my.getPk_id(),
				pagebean.getLimit_start(),pagebean.getLimit_offset());
		req.setAttribute("pagebean", pagebean);
		req.setAttribute("agoList", agoList);
		return "agorithmlist";
	}
	
	private AgorithmService agorithmService;
	private HeadLoad headLoad;
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
	public AgorithmService getAgorithmService() {
		return agorithmService;
	}
	public void setAgorithmService(AgorithmService agorithmService) {
		this.agorithmService = agorithmService;
	}
	public HeadLoad getHeadLoad() {
		return headLoad;
	}
	public void setHeadLoad(HeadLoad headLoad) {
		this.headLoad = headLoad;
	}
}

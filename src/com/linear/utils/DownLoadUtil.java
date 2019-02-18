package com.linear.utils;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownLoadUtil {
	public static File toFile(String filepath, HttpServletRequest req) {
		File file = null;
		if(filepath != null) {
			String []strs = filepath.split("/");
			if(strs.length >= 2) {
				String root = strs[0];
				String path = filepath.replace(root, "");
				
				file = new File(req.getServletContext().getRealPath(root), path);
			}
		}
		return file;
	}
	public static void download(String filepath, HttpServletRequest req, HttpServletResponse res) throws Exception {
		File file = DownLoadUtil.toFile(filepath, req);
		if(file!=null&&file.exists()) {
				String abpath = file.getAbsolutePath();
				//为了使下载框中显示中文名称不乱码
				String framename = new String(file.getName().getBytes("gbk"), "ISO-8859-1");
				
				
				
				String contentType = req.getServletContext().getMimeType(abpath);
				String disposition = "attachment;filename=" + framename;
				//设置头
				res.setHeader("Content-Type", contentType);
				res.setHeader("Content-Disposition", disposition);
				FileInputStream fis = new FileInputStream(file);
				
				ServletOutputStream sos = res.getOutputStream();
				//把文件的输入流中的内容 读取到输出流
				int len = 0;
				byte [] buff = new byte[1024];
				while((len = fis.read(buff)) != -1)
				{
					sos.write(buff, 0, len);
				}
				fis.close();
				sos.flush();
			}
		
	}
}

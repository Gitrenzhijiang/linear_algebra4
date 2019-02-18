package com.linear.utils;

import java.io.File;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import cn.ren.utils.CommonUtils;

public class UploadUtil {
	/**
	 * root是文件存储在项目里的根目录
	 * 可以为:/WEB-INF/files
	 * @param root
	 * @param fileItem
	 * @param req
	 */
	public UploadUtil(String root,FileItem fileItem,HttpServletRequest req) {
		this.root = root;
		this.fileItem = fileItem;
		this.req = req;
	}
	private FileItem fileItem;
	private String root;
	private HttpServletRequest req;
	private String relativePath = null;
	private File file;
	public String upload() {
		if(fileItem.isFormField())
			return null;
		String fileName = fileItem.getName();
		
		if(fileName!=null && !"".equals(fileName) && fileItem.getSize() > 0) {
			//处理上传的文件
			//处理fileName 是全路径的问题
			if(fileName.contains("/") || fileName.contains("\\")){
				int tempofpoint = fileName.lastIndexOf(".");
				fileName = new Random().nextInt(99999)+fileName.substring(tempofpoint);
			}
			/**
			 * 处理文件同名问题
			 * 
			 */
			String savename = CommonUtils.uuid() + "_" +fileName;
			/**
			 * 处理一个文件夹下放置多个文件的问题
			 * 使用hashcode  分目录
			 */
			int hashCode = fileName.hashCode();
			String hex = Integer.toHexString(hashCode);
			//获取WEB-INF/files的绝对路径
			String root = req.getServletContext().getRealPath(this.root);
			//保存到哪个文件夹?
			String path = "/" + hex.charAt(0) + "/" + hex.charAt(1);
			File dir = new File(root , path);
			dir.mkdirs();//创建所有必须但不存在的父目录
			//写入
			
			file = new File(root, path + "/" + savename);
			try {
				fileItem.write(file);
				this.relativePath = this.root + path + "/" + savename;
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		return relativePath;
	}
	/**
	 * 如果刚刚上传了文件，删除之
	 */
	public void delete() {
		if(file!=null) {
			if(file.exists())
				file.delete();
		}
	}
}

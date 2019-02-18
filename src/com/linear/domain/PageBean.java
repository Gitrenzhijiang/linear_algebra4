package com.linear.domain;

import java.util.ArrayList;
import java.util.List;

public class PageBean {
	private int totalnum = 0;//总记录数
	private int pageindex = 1;//当前页数
	private int totalpages = 0;//总页数
	private int pagesize = 10;//一页显示多少
	//知道了当前页的页码 pageindex,可以知道页码列表开始页码，结束页码
	private int start = 0;//
	private int end = 0;
	
	//DAO limit
	private int limit_start = 0;
	private int limit_offset = 0;
	public PageBean(int totalnum, String pageindex) {
		this.totalnum = totalnum;
		try {
			this.pageindex = Integer.parseInt(pageindex);
		}catch(Exception e) {
			this.pageindex = 1;
		}
		init();
	}
	
	public PageBean(int totalnum, String pageindex, int pagesize) {
		this.totalnum = totalnum;
		try {
			this.pageindex = Integer.parseInt(pageindex);
		}catch(Exception e) {
			this.pageindex = 1;
		}
		this.pagesize = pagesize;
		init();
	}
	
	private void init() {
		int temp = (pageindex - 1); 
		limit_start = (pageindex-1) * pagesize;
		limit_offset = pagesize;
		//总页数
		int t = totalnum / pagesize;
		totalpages = (totalnum % pagesize == 0)? t : t+1;
		start = (pageindex - 3 < 1)? 1 : pageindex - 3;
		end = (pageindex + 3 > totalpages)? totalpages : pageindex + 3;
	}
	/**
	 * 总页数
	 * @return
	 */
	public int getTotalpages() {
		return this.totalpages;
	}
	public int getPageindex() {
		return pageindex;
	}
	public boolean getIsfirst() {
		if(pageindex == start)
			return true;
		else 
			return false;
	}
	public boolean getIslast() {
		if(pageindex == end)
			return true;
		return false;
	}
	/**
	 * 页码列表第一个元素
	 * @return
	 */
	public int getStart() {
		return start;
	}
	public int getEnd() {
		return end;
	}
	public int getLimit_start() {
		return limit_start;
	}
	public int getLimit_offset() {
		return limit_offset;
	}
	public int getPreindex() {
		if(pageindex == start) {
			return start;
		}else {
			return pageindex - 1;
		}
	}
	public int getNextindex() {
		if(pageindex == end) {
			return end;
		}else {
			return pageindex + 1;
		}
	}
	public List<Integer> getPageList(){
		List<Integer> list = new ArrayList<Integer>();
		for(int i = start;i <= end;i++) {
			list.add(i);
		}
		return list;
	}
}

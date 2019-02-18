package com.linear.dao.imp;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.linear.dao.ResourceDao;
import com.linear.domain.Resource;

import cn.ren.jdbc.TxQueryRunner;

public class ResourceDaoImp implements ResourceDao{

	public void add(Resource res) {
		String sql = "insert into resource (role_id,url,method) values (?,?,?) ";
		try {
			qr.update(sql, res.getRole_id(),res.getUrl(),res.getMethod());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void deleteById(int id) {
		String sql = "delete from resource where pk_id = ?";
		try {
			qr.update(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<Resource> getList(int start, int num){
		String sql = "select * from resource limit ?,?";
		List<Resource> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Resource>(Resource.class), 
					start,num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	private QueryRunner qr = new TxQueryRunner();
	public int getTotalNum() {
		String sql = "select count(*) from resource ";
		int res = 0;
		Number n = 0;
		try {
			n = (Number) qr.query(sql, new ScalarHandler());
			res = n.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}

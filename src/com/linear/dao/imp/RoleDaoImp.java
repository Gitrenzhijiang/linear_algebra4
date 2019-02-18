package com.linear.dao.imp;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.linear.dao.RoleDao;
import com.linear.domain.Role;

import cn.ren.jdbc.TxQueryRunner;

public class RoleDaoImp implements RoleDao{

	public Role getById(int id) {
		String sql = "select pk_id,name,descript from role where pk_id = ?";
		Role r =  null;
		try {
			r = qr.query(sql, new BeanHandler<Role>(Role.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}
	
	private QueryRunner qr = new TxQueryRunner();

	public List<Role> getList() {
		List<Role> list =  null;
		String sql = "select * from role";
		try {
			list = qr.query(sql, new BeanListHandler<Role>(Role.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}

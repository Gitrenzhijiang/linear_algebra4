package com.linear.test;



import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import com.linear.domain.User;

import cn.ren.jdbc.TxQueryRunner;

public class DBtest {
	@Test
	public void get() {
		
		try {
			QueryRunner qr = new TxQueryRunner();
			String sql = "select * from user";
			List<User> users = qr.query(sql, new BeanListHandler<>(User.class));
			for(User u:users) {
				System.out.println(u.getUser_no() + "--" + u.getName());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

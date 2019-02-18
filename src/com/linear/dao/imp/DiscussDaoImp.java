package com.linear.dao.imp;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.linear.dao.DiscussDao;
import com.linear.domain.Discuss;

import cn.ren.jdbc.TxQueryRunner;

public class DiscussDaoImp implements DiscussDao{
	
	@Override
	public List<Discuss> getListByAnswer_id(int answer_id) {
		List<Discuss> list = null;
		String sql = "select * from discuss where answer_id = ? order by spoketime";
		try {
			list = qr.query(sql, new BeanListHandler<Discuss>(Discuss.class), answer_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	private QueryRunner qr = new TxQueryRunner();
}

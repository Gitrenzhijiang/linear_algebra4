package com.linear.dao.imp;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.linear.dao.AgorithmDao;
import com.linear.dao.DaoException;
import com.linear.domain.Agorithm;

import cn.ren.jdbc.TxQueryRunner;

public class AgorithmDaoImp implements AgorithmDao{

	public void add(Agorithm ago) {
		String sql = "insert into agorithm (name,filepath,user_id) "
				+ "values (?,?,?)";
		try {
			qr.update(sql, ago.getName(),ago.getFilepath(),ago.getUser_id());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private QueryRunner qr = new TxQueryRunner();
	
	public int getTotalNumByUser_id(int user_id) {
		String sql = "select count(*) from agorithm where user_id = ? ";
		int res = 0;
		Number number = 0;
		try {
			number = (Number) qr.query(sql, new ScalarHandler(), user_id);
			res = number.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public List<Agorithm> getListByUser_id(int user_id, int limit_start, int limit_offset) {
		String sql = "select * from agorithm where user_id = ? limit ?,?";
		List<Agorithm> list = null;
		try {
			 list = qr.query(sql, new BeanListHandler<Agorithm>(Agorithm.class), 
					 user_id, limit_start,limit_offset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Agorithm getById(int ago_id) {
		String sql = "select * from agorithm where pk_id = ?";
		Agorithm ago = null;
		try {
			ago = qr.query(sql, new BeanHandler<Agorithm>(Agorithm.class), ago_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ago;
	}

	public void deleteById(int ago_id) throws DaoException {
		String sql = "delete from agorithm where pk_id = ?";
		try {
			qr.update(sql, ago_id);
		} catch (SQLException e) {
			throw new DaoException("删除agorithm失败");
		}
	}
}

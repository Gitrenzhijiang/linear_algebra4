package com.linear.dao.imp;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.linear.dao.ClickgoodDao;
import com.linear.domain.Clickgood;

import cn.ren.jdbc.TxQueryRunner;

public class ClickgoodDaoImp implements ClickgoodDao{
	
	/**
	 *返回这个回答的好评数量
	 * @param pk_id
	 * @return
	 */
	public int getNumOfOkByAnswer_id(int answer_id) {
		return getNumByAnswer_id(answer_id, 1);
	}
	/**
	 * 返回这个回答的差评数量
	 * @param answer_id
	 * @return
	 */
	public int getNumOfErrorByAnswer_id(int answer_id) {
		return getNumByAnswer_id(answer_id, 0);
	}
	
	private int getNumByAnswer_id(int answer_id, int isgood) {
		String sql = "select count(*) from clickgood where answer_id = ? and isgood = ?";
		int res = 0;
		Number n = 0;
		try {
			n = (Number) qr.query(sql, new ScalarHandler(), answer_id, isgood);
			res = n.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	private QueryRunner qr = new TxQueryRunner();
	
	public void clickOfisgoodToAnswerByUser_id(int a_id,int user_id, int is_good) {
		String sql = "insert into clickgood (answer_id,clickuser_id,isgood)"
				+ " values (?,?,?)";
		try {
			qr.update(sql, a_id,user_id,is_good);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Clickgood getByUser_idAndAnswer_id(int user_id, int a_id) {
		String sql = "select * from clickgood where answer_id = ? and clickuser_id = ?";
		Clickgood cg = null;
		try {
			cg = qr.query(sql, new BeanHandler<Clickgood>(Clickgood.class), a_id,user_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cg;
	}
	public List<Clickgood> getByAnswer_id(int answer_id) {
		String sql = "select * from clickgood where answer_id = ?";
		List<Clickgood> cgList = null;
		try {
			cgList = qr.query(sql, new BeanListHandler<Clickgood>(Clickgood.class), answer_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cgList;
	}

}

package com.linear.dao.imp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.linear.dao.ProblemDao;
import com.linear.domain.Problem;

import cn.ren.jdbc.TxQueryRunner;

public class ProblemDaoImp implements ProblemDao{

	public List<Problem> getList(int start, int num) {
		String sql = "select * from problem order by createtime desc limit ?,?";
		List<Problem> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Problem>(Problem.class), start, num);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	private QueryRunner qr = new TxQueryRunner();
	public int getProblemNum() {
		String sql = "select count(*) from problem";
		int res = 0;
		Number number=0;
		try {
			number = (Number) qr.query(sql, new ScalarHandler());
			res = number.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public void add(Problem p) {
		String sql = "insert into problem (title,level,descript,qpicpath,user_id,issolved,createtime)"
				+ " values (?,?,?,?,?,?,?)";
		try {
			qr.update(sql, p.getTitle(),p.getLevel(),p.getDescript(),p.getQpicpath(),p.getUser_id(),p.getIssolved(),new Timestamp(p.getCreatetime().getTime()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public int getProblemNumOfLevel(int level) {
		String sql = "select count(*) from problem where level = ?";
		int res = 0;
		Number number=0;
		try {
			number = (Number) qr.query(sql, new ScalarHandler(), level);
			res = number.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public int getProblemNumOfIssolved(int issolved) {
		String sql = "select count(*) from problem where issolved = ?";
		int res = 0;
		Number number=0;
		try {
			number = (Number) qr.query(sql, new ScalarHandler(), issolved);
			res = number.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public List<Problem> getListOfLevel(int level, int limit_start, int limit_offset) {
		String sql = "select * from problem where level = ? order by createtime desc limit ?,?";
		List<Problem> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Problem>(Problem.class), level, limit_start, limit_offset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Problem> getListOfIssolved(int issolved, int limit_start, int limit_offset) {
		String sql = "select * from problem where issolved = ? order by createtime desc limit ?,?";
		List<Problem> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Problem>(Problem.class), issolved, limit_start, limit_offset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<Problem> getListOfUser_id(int user_id, int limit_start, int limit_offset) {
		String sql = "select * from problem where user_id = ? order by createtime desc limit ?,?";
		List<Problem> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Problem>(Problem.class), user_id, limit_start, limit_offset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public Problem getById(int pid) {
		String sql = "select * from problem where pk_id = ?";
		Problem p = null;
		try {
			p = qr.query(sql, new BeanHandler<Problem>(Problem.class), pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	public int getProblemNumByUser_id(int user_id) {
		String sql = "select count(*) from problem where user_id = ?";
		int res = 0;
		Number number=0;
		try {
			number = (Number) qr.query(sql, new ScalarHandler(), user_id);
			res = number.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public void updatelevel(int problem_id, int level) {
		String sql = "update problem set level = ? where pk_id = ?";
		try {
			qr.update(sql, level, problem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateissolved(int problem_id, int i) {
		String sql = "update problem set issolved = ? where pk_id = ?";
		try {
			qr.update(sql, i, problem_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

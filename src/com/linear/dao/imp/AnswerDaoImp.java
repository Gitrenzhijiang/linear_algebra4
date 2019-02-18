package com.linear.dao.imp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.linear.dao.AnswerDao;
import com.linear.domain.Answer;
import com.linear.domain.Discuss;

import cn.ren.jdbc.TxQueryRunner;

public class AnswerDaoImp implements AnswerDao{

	public List<Answer> getList(int pid,int start, int end) {
		String sql = "select * from answer where problem_id = ? order by answertime limit ?,?";
		List<Answer> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<Answer>(Answer.class),pid, start,end);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void add(Answer answer) {
		String sql = "insert into answer (problem_id,user_id,answertime,apicpath,answertext) values"
				+ " (?,?,?,?,?)";
		try {
			qr.update(sql, answer.getProblem_id(),answer.getUser_id(),
					new Timestamp(answer.getAnswertime().getTime()), 
					answer.getApicpath(),answer.getAnswertext());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private QueryRunner qr = new TxQueryRunner();

	public int getTotalAnswerNumOfProblemId(int pid) {
		String sql = "select count(*) from answer where problem_id = ?";
		int res = 0;
		Number n = 0;
		try {
			n = (Number) qr.query(sql, new ScalarHandler(), pid);
			res = n.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public Answer getById(int a_id) {
		String sql = "select * from answer where pk_id = ?";
		Answer answer = null;
		try {
			answer = qr.query(sql, new BeanHandler<Answer>(Answer.class), a_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return answer;
	}

	public void addDiscuss(Discuss discuss) {
		String sql = "insert into discuss (answer_id,spokesman_id,spoketime,spokestext) "
				+ " values (?,?,?,?)";
		try {
			qr.update(sql, discuss.getAnswer_id(),discuss.getSpokesman_id(),
					new Timestamp(discuss.getSpoketime().getTime()),discuss.getSpokestext());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

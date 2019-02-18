package com.linear.dao.imp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.linear.dao.UserDao;
import com.linear.domain.User;

import cn.ren.jdbc.TxQueryRunner;

public class UserDaoImp implements UserDao{
	
	/**
	 * 
	 * @param no 账号
	 * @return 如果存在返回user,如果不存在，返回null
	 */
	public User getByUser_no(String no) {
		String sql = "select pk_id,user_no,name,password,email,headpic,descript,ctime,role_id,activation from user where user_no = ?";
		User u = null;
		try {
			u = qr.query(sql, new BeanHandler<User>(User.class), no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}
	/**
	 * 返回所有属于该角色ID 的用户列表
	 * 如果没有一条数据被查出，返回一个size=0的list
	 * @param role_id
	 * @return
	 */
	public List<User> getListByRole_id(int role_id){
		List<User> list = new ArrayList<User>();
		String sql = "select pk_id,user_no,name,password,email,headpic,descript,ctime,role_id,activation from user where role_id = ?";
		try {
			list = qr.query(sql, new BeanListHandler<User>(User.class), role_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public void deleteByPk_id(int id) {
		String sql = "delete from user where pk_id = ?";
		try {
			qr.update(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 添加用户
	 * @param user
	 */
	public void add(User user) {
		String sql = "insert into user (user_no, name, password, email,ctime, role_id, activation, headpic)" + 
				" values " + 
				"( ?,?,?,?,?,?,?,?)";
		try {
			qr.update(sql, new Object[] {user.getUser_no(), user.getName(),user.getPassword(),user.getEmail(),new Timestamp(user.getCtime().getTime()),user.getRole_id(),user.getActivation(),user.getHeadpic()});
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询User
	 * @param pk_id
	 * @return
	 */
	public User getById(int pk_id) {
		String sql = "select pk_id,user_no,name,password,email,headpic,descript,ctime,role_id,activation from user where pk_id = ?";
		User u = null;
		try {
			u = qr.query(sql, new BeanHandler<User>(User.class), pk_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return u;
	}
	/**
	 * 更新
	 * @param u
	 */
	public void update(User u) {
		String sql = "update user set name=?, password=?,email=?,headpic=?,descript=?,ctime=?,activation=? where pk_id = ?";
		try {
			qr.update(sql, new Object[] {u.getName(),u.getPassword(),u.getEmail(),u.getHeadpic(),u.getDescript(),new Timestamp(u.getCtime().getTime()),u.getActivation(), u.getPk_id()});
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private QueryRunner qr = new TxQueryRunner();
	public List<User> getListNotIclude(int user_id) {
		String sql = "select * from user where pk_id != ?";
		List<User> list = null;
		try {
			list = qr.query(sql, new BeanListHandler<User>(User.class), user_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public List<User> getTeaListByActivation(int activation, int start, int offset) {
		List<User> list = null;
		String sql = "select pk_id,user_no,name,password,email,headpic,descript,ctime,role_id,activation from user where role_id = 2 and activation = ? limit ?,?";
		try {
			list = qr.query(sql, new BeanListHandler<User>(User.class), activation, start, offset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public int getTeaNumByActivation(int activation) {
		int res = 0;
		String sql = "select count(*) from user where role_id = 2 and activation = ?";
		Number num = 0;
		try {
			num = (Number) qr.query(sql, new ScalarHandler(), activation);
			res = num.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	
}

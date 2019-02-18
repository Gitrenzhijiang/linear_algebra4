package com.linear.dao.imp;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.linear.dao.TeachresourceDao;
import com.linear.domain.Teachresource;

import cn.ren.jdbc.TxQueryRunner;

public class TeachresourceDaoImp implements TeachresourceDao{
	/**
	 * 添加
	 * @param t
	 */
	public void add(Teachresource t) {
		String sql = "insert into teachresource (rname, rfilepath, descript, uploadtime, upload_id) "
				+ " values "
				+ "(?,?,?,?,?)";
		try {
			qr.update(sql, new Object[] {t.getRname(),t.getRfilepath(),t.getDescript(),new Timestamp(t.getUploadtime().getTime()),t.getUpload_id()});
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(int id) {
		String sql = "delete from teachresource where pk_id = ?";
		try {
			qr.update(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询多个
	 * @param id upload_id
	 * @return
	 */
	public List<Teachresource> getListByUpload_id(int id, int start, int offset){
		List<Teachresource> list = new ArrayList<Teachresource>();
		String sql = "select pk_id,rname,rfilepath,descript,uploadtime,upload_id from teachresource where upload_id = ? order by uploadtime desc limit ?,?";
		try {
			list = qr.query
					(sql, new BeanListHandler<Teachresource>(Teachresource.class), id, start, offset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private QueryRunner qr = new TxQueryRunner();

	public int getTotalNum() {
		String sql = "select count(*) from teachresource";
		int num = 0;
		try {
			 Number number = (Number) qr.query(sql, new ScalarHandler());
			 num = number.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
	public List<Teachresource> getList(int start, int offset) {
		List<Teachresource> list = new ArrayList<Teachresource>();
		String sql = "select pk_id,rname,rfilepath,descript,uploadtime,upload_id from teachresource order by uploadtime desc limit ?,?";
		try {
			list = qr.query
					(sql, new BeanListHandler<Teachresource>(Teachresource.class), start,offset);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public Teachresource getById(int pk_id) {
		String sql = "select * from teachresource where pk_id = ?";
		Teachresource tr = null;
		try {
			tr = qr.query(sql, new BeanHandler<Teachresource>(Teachresource.class), pk_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tr;
	}
	public int getTotalNumByUpload_id(int upload_id) {
		String sql = "select count(*) from teachresource where upload_id = ?";
		int num = 0;
		try {
			 Number number = (Number) qr.query(sql, new ScalarHandler(), upload_id);
			 num = number.intValue();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
}

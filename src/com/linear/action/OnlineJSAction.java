package com.linear.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linear.matrix.Matrix;
import com.linear.matrix.MatrixExe;
import com.linear.matrix.SMatrix;
import com.linear.test.MatrixEigenValue;
import com.ren.struts.core.Action;
import com.ren.struts.core.RequestAware;
import com.ren.struts.core.ResponseAware;

public class OnlineJSAction implements Action,RequestAware,ResponseAware{
	
	public String tojzxs2() {
		headLoad.loadHead(req);
		return "jzxs";
	}
	/**
	 * 矩阵相似
	 * @return
	 */
	public String jzxs() {
		String arr1 = req.getParameter("arr1");
		String arr2 = req.getParameter("arr2");
		String row = req.getParameter("row");
		String res = "''";
		try {
			int r = Integer.parseInt(row);
			Matrix m1 = new Matrix(r, r, string2arr(arr1, r, r));
			Matrix m2 = new Matrix(r, r, string2arr(arr2, r, r));
			res = MatrixExe.isxs(m1, m2) == true ? "yes":"no";
			this.res.getWriter().println(res);
			this.res.getWriter().flush();
		}catch(Exception e) {
			
		}
		return null;
	}
	
	public String jzxxtzz() {
		String arr = req.getParameter("arr");
		String row = req.getParameter("row");
		String col = req.getParameter("col");
		try {
			int int_row = Integer.parseInt(row);
			int int_col = Integer.parseInt(col);
			Matrix m = new Matrix(int_row, int_col, string2arr(arr, int_row, int_col));
			boolean t = false;
			try {
				SMatrix sm = new SMatrix(m);
				t = MatrixExe.islinear(sm);//线性相关判断
			}catch(Exception e) {
				
			}
			String tzz = "''";
			try {
				if(m.getRow() == m.getCol()) {//有特征值必须是row = col
					double[][] GetEV = new double [m.getRow()][m.getRow()];
					MatrixEigenValue.EigenValue(m.getData(), m.getRow(), 400, 4, GetEV);
					tzz = "'";
					for (int i = 0; i < GetEV.length; i++) {
						String str = String.valueOf(GetEV[i][0]);//
						tzz += (str + "\t");
					}
					tzz += "'";
				}
			}catch(Exception e) {
				
			}
			res.getWriter().println("[" + (t==false?"\'no\'":"\'yes\'") + "," + tzz + "]");
			res.getWriter().flush();
		}catch(Exception e) {
			//e.printStackTrace();
			
		}
		return null;
	}
	/**
	 * 矩阵线性相关与特征值
	 * @return
	 */
	public String tojzxxtzz() {
		headLoad.loadHead(req);
		return "jzxxtzz";
	}
	
	/**
	 * 跳转到求逆矩阵
	 * @return
	 */
	public String tonizzz() {
		headLoad.loadHead(req);
		return "nizzz";
	}
	
	public String nizzz() {
		String arr = req.getParameter("arr");
		String row = req.getParameter("row");
		String col = req.getParameter("col");
		try {
			int int_row = Integer.parseInt(row);
			int int_col = Integer.parseInt(col);
			double [][] data = string2arr(arr, int_row, int_col);
			Matrix m = new Matrix(int_row, int_col, data);
			SMatrix sm = new SMatrix(m);
			int z = 0;
			try {
				z = MatrixExe.decade(sm);//矩阵的秩
			}catch(Exception e) {
				
			}
			Matrix ni = null;
			try {
				ni = MatrixExe.contrary(m);//逆矩阵
			}catch(Exception e) {
				
			}
			Matrix zz = null;
			try {
				zz = MatrixExe.transposition(m);//转置
			}catch(Exception e) {
				
			}
			StringBuffer sb = new StringBuffer();
			sb.append("[" + z + ",");
			sb.append(matrix2string(ni) + ",");
			sb.append(matrix2string(zz));
			sb.append("]");
			res.getWriter().println(sb.toString());
			res.getWriter().flush();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 跳转到行列式求值
	 * @return
	 */
	public String tohlsqz() {
		headLoad.loadHead(req);
		return "hlsqz";
	}
	
	public String hlsqz() {
		String arr = req.getParameter("arr");
		String num = req.getParameter("num");
		try {
			int row = Integer.parseInt(num);
			
			double [][] data = string2arr(arr, row, row);
			
			Matrix m = new Matrix(row, row, data);
			res.getWriter().println(MatrixExe.getValue_hls(m));
			res.getWriter().flush();
		}catch(Exception e) {
			return null;
		}
		return null;
	}

	
	
	public String juzjiachen() {
		String arr1 = req.getParameter("arr1");
		String arr2 = req.getParameter("arr2");
		String arow = req.getParameter("arow");
		String acol = req.getParameter("acol");
		String brow = req.getParameter("brow");
		String bcol = req.getParameter("bcol");
		try {
			int int_arow = Integer.parseInt(arow);
			int int_acol = Integer.parseInt(acol);
			int int_brow = Integer.parseInt(brow);
			int int_bcol = Integer.parseInt(bcol);
			double[][] data1 = string2arr(arr1, int_arow, int_acol);
			double[][] data2 = string2arr(arr2, int_brow, int_bcol);
			
			Matrix m1 = new Matrix(int_arow, int_acol, data1);
			Matrix m2 = new Matrix(int_brow, int_bcol, data2);
			Matrix addm = null;
			try {
				 addm = MatrixExe.add(m1, m2);
			}catch(Exception e) {
				
			}
			Matrix cm = null;
			try {
				cm = MatrixExe.multia(m1, m2);
			}catch(Exception e) {
				
			}
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			sb.append(matrix2string(addm)+",");
			sb.append(matrix2string(cm));
			sb.append("]");
			res.getWriter().println(sb.toString());
			res.getWriter().flush();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 将字符串转换成double数组
	 * @param arr
	 * @param row
	 * @param col
	 * @return
	 */
	private double[][] string2arr(String arr, int row, int col) {
		String[]arrs = arr.split(",");
		double darrs[]=new double[arrs.length];
		for(int i = 0;i < darrs.length;i++)
			darrs[i] = Double.parseDouble(arrs[i]);
		double [][] data = new double[row][col];
		for(int i = 0;i < row;i++) {
			for(int j = 0;j < col;j++) {
				data[i][j] = darrs[i * col + j];
			}
		}
		return data;
	}
	private String matrix2string(Matrix m) {
		if(m == null) {
			return "{}";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(int i = 0;i < m.getRow();i++) {
			for(int j = 0;j < m.getCol();j++) {
				sb.append(m.get(i+1, j+1) + ",");
			}
		}
		if(sb.toString().endsWith(",")) {
			sb.replace(sb.length()-1, sb.length(), "]");
		}
		String res = "{'row':"+m.getRow()+",'col':" + m.getCol()+",'data':" + sb.toString() +"}";
		return res;
	}
	
	
	
	public String tojuzjiachen() {
		headLoad.loadHead(req);
		return "juzjiachen";
	}
	
	
	
	@Override
	public void requestAware(HttpServletRequest req) {
		this.req = req;
	}
	private HttpServletRequest req;
	private HttpServletResponse res;
	private HeadLoad headLoad;
	@Override
	public String execute() {
		return null;
	}
	@Override
	public void responseAware(HttpServletResponse res) {
		this.res = res;
	}
	public HeadLoad getHeadLoad() {
		return headLoad;
	}
	public void setHeadLoad(HeadLoad headLoad) {
		this.headLoad = headLoad;
	}
}

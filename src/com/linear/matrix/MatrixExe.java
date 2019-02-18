package com.linear.matrix;

import com.linear.test.MatrixEigenValue;

public class MatrixExe{
	/**
	 * 行列式求值
	 * @param m 行数=列数
	 * @return
	 */
	public static double getValue_hls(Matrix m) {
		if(m.getRow() != m.getCol())
			throw new RuntimeException("行数不等于列数");
		if(m.getRow() == 1) {
			return m.get(1, 1);
		}
		if(m.getRow() == 2) {
			return (m.get(1, 1)*m.get(2, 2))-(m.get(1, 2)*m.get(2, 1));
		}
		//开始递归[
		long v = 0;
		for(int i = 0;i < m.getRow();i++) {
			v += (m.get(i+1, 1) * getValue_hls(m.getYZS(i+1, 1)) * Math.pow(-1, (i+2)));
		}
		return v;
	}
	/**
	 * 矩阵加法,返回一个新矩阵
	 * @param m1
	 * @param m2
	 * @return
	 */
	public static Matrix add(Matrix m1, Matrix m2) {
		if(m1.getRow() != m2.getRow() || m1.getCol() != m2.getCol())
			throw new RuntimeException("两个矩阵不能相加");
		double [][] data = new double[m1.getRow()][m1.getCol()];
		for(int i = 0;i < m1.getRow();i++) {
			for(int j = 0;j < m1.getCol();j++) {
				data[i][j] = m1.get(i+1, j+1) + m2.get(i+1, j+1);
			}
		}
		return new Matrix(m1.getRow(), m1.getCol(), data);
	}
	/**
	 * 矩阵乘法
	 * @param m1
	 * @param m2
	 * @return
	 */
	public static Matrix multia(Matrix m1, Matrix m2) {
		if(m1.getCol() != m2.getRow())
			throw new RuntimeException("两个矩阵不能相乘");
		double [][] data = new double[m1.getRow()][m2.getCol()];
		for(int i = 0;i < m1.getRow();i++) {
			for(int j = 0;j < m2.getCol();j++) {
				double res = 0;
				for(int k = 0;k < m1.getCol();k++) {
					res += (m1.get(i+1, k+1) * m2.get(k+1, j+1));
				}
				data[i][j] = res;
			}
		}
		return new Matrix(m1.getRow(), m2.getCol(), data);
	}
	/**
	 * 矩阵转置
	 * @param m
	 * @return
	 */
	public static Matrix transposition(Matrix m) {
		double [][] data = new double[m.getCol()][m.getRow()];
		for(int i = 0;i < m.getCol();i++) {
			for(int j = 0;j < m.getRow();j++) {
				data[i][j] = m.get(j+1, i+1);
			}
		}
		return new Matrix(m.getCol(), m.getRow(), data);
	}
	/**
	 * 一个数乘以一个矩阵
	 * @param m
	 * @param n
	 * @return
	 */
	public static Matrix nmultia(Matrix m, double n) {
		double [][] data = new double[m.getRow()][m.getCol()];
		for(int i = 0;i < m.getRow();i++) {
			for(int j = 0;j < m.getCol();j++) {
				data[i][j] = m.get(i+1, j+1) * n;
			}
		}
		return new Matrix(m.getRow(),m.getCol(),data);
	}
	/**
	 * 逆矩阵
	 * @param m
	 * @return
	 */
	public static Matrix contrary(Matrix m) {
		if(m.getRow() != m.getCol()) {//该矩阵没有逆矩阵
			throw new RuntimeException("无逆矩阵");
		}
		//开判断矩阵行列式的值
		if(MatrixExe.getValue_hls(m) == 0) {
			throw new RuntimeException("无逆矩阵");
		}
		//求伴随矩阵 
		double [][] tbsdata = new double[m.getRow()][m.getCol()];
		for(int i = 0;i < m.getRow();i++) {
			for(int j = 0;j < m.getCol();j++) {
				tbsdata[i][j] = (double) (MatrixExe.getValue_hls(m.getYZS(j+1, i+1)) * Math.pow(-1, (i+j+2)));
			}
		}
		Matrix tbs = new Matrix(m.getRow(), m.getCol(), tbsdata);
		return MatrixExe.nmultia(tbs, 1.0/MatrixExe.getValue_hls(m));
	}
	/**
	 * 矩阵的秩
	 * @param m
	 * @return
	 */
	public static int decade(SMatrix m) {
		if(m.iszero())return 0;//零矩阵
		int k = 1;//开始依据第一行为基准
		
		for(int i = 0;i < m.getCol();i++) {
			//第一列
			for(int c = m.getRow();c>k;c--) {
				//如果当前行这个元素是0
				if(m.get(c, i+1).equals(new Decimal("0")))
					continue;
				//(当前行这元素非0，如果基准行那个元素是0
				if(m.get(k, i+1).equals(new Decimal("0"))) {
					m.exchange(k, c);
				}else {
					m.rowadd(c, k, m.get(c, i+1).copy().divide(m.get(k, i+1)).multiply(new Decimal("-1")));
				}
			}
			//更新基准
			int newt = 0;
			for(newt = m.getRow();newt > 0;newt--) {
				if(!m.get(newt, i+1).equals(new Decimal("0"))) {
					break;
				}
			}
			k = Math.max(k, newt+1);
			if(i == m.getCol() - 1) {
				return k > m.getRow() ? m.getRow() : k - 1;
			}
		}
		
		return 0;
		
	}
	/**
	 * 线性相关判断
	 * @param sm
	 * @return
	 */
	public static boolean islinear(SMatrix sm) {
		if(MatrixExe.decade(sm) < sm.getCol())
			return true;
		return false;
	}
	/**
	 * 矩阵是否相似
	 * @return
	 */
	public static boolean isxs(Matrix m1, Matrix m2) {
		
		if(m1.getRow() == m1.getCol() && m2.getRow() == m2.getCol() && m1.getRow() == m2.getRow()) {
			double [][] ReV1 = new double [m1.getRow()][m1.getRow()];
			double [][] ReV2 = new double [m1.getRow()][m1.getRow()];
			MatrixEigenValue.EigenValue(m1.getData(), m1.getRow(), 400, 4, ReV1);
			MatrixEigenValue.EigenValue(m2.getData(), m2.getRow(), 400, 4, ReV2);
			
			boolean t = fun0_isxs(ReV1, ReV2);
			if(t == false)
				return false;//特征值不同，一定不相似
			//验证相似的必要条件：Ra=Rb
			SMatrix sm1 = new SMatrix(m1);
			SMatrix sm2 = new SMatrix(m2);
			if(MatrixExe.decade(sm1) != MatrixExe.decade(sm2)) {
				return false;
			}
			//特征值相同
			//1.是否有无重复的特征值
			if(fun1_isxs(ReV1) == false) {
				return true;//特征值相同，但没有重复的特征值，相似
			}else {
				//特征值有重复的
				return true;//???
			}
			
		}
		return false;
	}
	public static void main(String[] args) {
		double [][] m = {{0,-1,1},{-2,0,2},{1,1,0}};
		double [][] v = {{-2,0,0},{0,2,0},{0,0,1}};
		Matrix m1 = new Matrix(3, 3, m);
		Matrix m2 = new Matrix(3, 3, v);
		System.out.println(isxs(m1, m2));
	}
	//特征值是否相同
	private static boolean fun0_isxs(double [][] v, double [][] m) {
		for(int i = 0;i < v.length;i++) {
			double d = v[i][0];
			boolean t = false;
			for(int j = 0;j < m.length;j++) {
				if(Math.abs(d - m[j][0]) < 0.00001) {
					t = true;
					break;
				}
			}
			if(t == false)return false;
		}
		return true;
	}
	
	//是否有重复的特征值
	private static boolean fun1_isxs(double[][]v) {
		for(int i = 0;i < v.length;i++) {
			for(int j = i+1;j < v.length;j++) {
				if(Math.abs(v[i][0] - v[j][0]) < 0.00001) {
					return true;
				}
			}
		}
		return false;
	}
	public static void printMatrix(Matrix m) {
		for(int i = 0;i < m.getRow();i++) {
			for(int j = 0;j < m.getCol();j++) {
				System.out.print(m.get(i+1, j+1)+ "\t");
			}
			System.out.println();
		}
	}
	
}

package com.linear.matrix;


public class Matrix{
	public Matrix(int row, int col, double[][]data) {
		this.data = data;
		this.row = row;
		this.col = col;
	}
	/**
	 * 得到行列式中的值
	 * @param r 行 
	 * @param c 列
	 * @return
	 */
	public double get(int r, int c) {
		return data[r-1][c-1];
	}
	public void set(int r, int c, double d) {
		data[r-1][c-1] = d;
	}
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	public double [][] getData(){
		return this.data;
	}
	/**
	 * 返回这个的余子式
	 * 以第i行第j列
	 * @return
	 */
	public Matrix getYZS(double i,double j){
		if(this.getRow() <= 1)
			throw new RuntimeException("行数<=1,无余子式");
		double [][] yzsdata = new double[row-1][col-1];
		int index_x=0,index_y=0;
		for(int x = 0; x < row;x++) {
			if(x+1 == i)
				continue;
			for(int y = 0;y < col;y++) {
				if(y+1 == j)
					continue;
				yzsdata[index_x][index_y] = data[x][y]; 
				index_y++;
				if(index_y >= col-1) {
					index_y=0;index_x++;
				}
			}
		}
		
		return new Matrix(row-1, col-1, yzsdata);
	}
	
	
	
	
	public Matrix copy() {
		double[][] data = new double[this.getRow()][getCol()];
		for(int i = 0;i < this.getRow();i++) {
			for(int j = 0;j < this.getCol();j++) {
				data[i][j] = this.get(i+1, j+1);
			}
		}
		return new Matrix(getRow(),getCol(),data);
	}
	/**
	 * 判断矩阵是否是零矩阵
	 * @return
	 */
	public boolean iszero() {
		for(int i = 0;i < this.getRow();i++) {
			for(int j = 0;j < this.getCol();j++) {
				if(this.get(i+1, j+1) != 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	/*存放矩阵数据*/
	private double [][] data;
	/*矩阵行数*/
	private int row;
	/*矩阵列数*/
	private int col;
}


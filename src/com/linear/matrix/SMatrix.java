package com.linear.matrix;


public class SMatrix {
	public SMatrix(Matrix m) {
		this.m = m;
		data = new Decimal[m.getRow()][m.getCol()];
		for(int i = 0;i < m.getRow();i++) {
			for(int j = 0;j < m.getCol();j++) {
				data[i][j] = new Decimal(m.get(i+1, j+1)+"");
			}
		}
	}
	public boolean iszero() {
		return m.iszero();
	}
	public int getRow() {
		return m.getRow();
	}
	public int getCol() {
		return m.getCol();
	}
	public Decimal get(int r, int c) {
		return data[r-1][c-1];
	}
	public void set(int r, int c, Decimal d) {
		data[r-1][c-1] = d;
	}
	/**
	 * 交换两行数据
	 * @param row1
	 * @param row2
	 */
	public void exchange(int row1, int row2) {
		if(row1 != row2 && 1<=row1 && row1<=this.getRow() && 1<=row2 && row2<=this.getRow()) {
			for(int i = 0;i < this.getCol();i++) {
				Decimal temp = this.get(row1, i+1);
				this.set(row1, i+1, get(row2, i+1));
				this.set(row2, i+1, temp);
			}
		}
	}
	public void rowmul(int row, Decimal num) {
		if(1 <= row && row <= getRow()) {
			for(int i = 0;i < getCol();i++) {
				get(row, i+1).multiply(num);
			}
		}
	}
	/**
	 * 某一行加上另外一行的K倍
	 * row1 这一行加上row2这一行的K倍
	 */
	public void rowadd(int row1, int row2, Decimal k) {
		if(row1 != row2 && 1<=row1 && row1<=this.getRow() && 1<=row2 && row2<=this.getRow()) {
			for(int i = 0;i < this.getCol();i++) {
				get(row1, i+1).add(get(row2,i+1).copy().multiply(k));
			}
		}
	}
	
	public void print() {
		for(int i = 0;i < getRow();i++) {
			for(int j = 0;j < getCol();j++) {
				System.out.print(this.get(i+1, j+1) + "\t");
			}
			System.out.println();
		}
	}
	
	
	
	/*起初的矩阵*/
	private Matrix m;
	/*存放矩阵数据*/
	private Decimal [][] data;
	
}

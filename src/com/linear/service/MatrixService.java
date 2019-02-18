package com.linear.service;

import com.linear.matrix.Matrix;
import com.linear.matrix.MatrixExe;
import com.linear.matrix.SMatrix;

/**
 * 矩阵和行列式在线运算
 * @author 任志江
 *
 */
public class MatrixService {
	public static void main(String[] args) {
		double[][] data = {
				{3,2,0,5,0},
				{2,0,1,5,-3},
				{3,-2,3,6,-1},
				{1,6,-4,-1,4}
		};
		double[][] data2 = {
				{1,2,3},
				{2,3,-5},
				{4,7,1}
		};
		double[][] data3 = {
				
				{0,6,2,-4,10},
				{-2,1,0,-3,2},
				{0,0,0,0,0},
				{0,0,0,4,-3}
				
		};
		Matrix m = new Matrix(4, 5, data);
		SMatrix sm = new SMatrix(m);
		sm.print();
		System.out.println(MatrixExe.decade(sm));
		sm.print();
	}
}





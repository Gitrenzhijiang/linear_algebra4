package com.linear.test;

public class MatrixEigenValue {
	public static int Hessenberg(double[][] Matrix, int n, double[][] ret) {

		int i;
		int j;
		int k;
		double temp;
		int MaxNu;
		n -= 1;
		for (k = 1; k <= n - 1; k++) {
			i = k - 1;
			MaxNu = k;
			temp = Math.abs(Matrix[k][i]);
			for (j = k + 1; j <= n; j++) {
				if (Math.abs(Matrix[j][i]) > temp) {
					MaxNu = j;
				}
			}
			ret[0][0] = Matrix[MaxNu][i];
			i = MaxNu;
			if (ret[0][0] != 0) {
				if (i != k) {
					for (j = k - 1; j <= n; j++) {
						temp = Matrix[i][j];
						Matrix[i][j] = Matrix[k][j];
						Matrix[k][j] = temp;
					}
					for (j = 0; j <= n; j++) {
						temp = Matrix[j][i];
						Matrix[j][i] = Matrix[j][k];
						Matrix[j][k] = temp;
					}
				}
				for (i = k + 1; i <= n; i++) {
					temp = Matrix[i][k - 1] / ret[0][0];
					Matrix[i][k - 1] = 0;
					for (j = k; j <= n; j++) {
						Matrix[i][j] -= temp * Matrix[k][j];
					}
					for (j = 0; j <= n; j++) {
						Matrix[j][k] += temp * Matrix[j][i];
					}
				}
			}
		}
		for (i = 0; i <= n; i++) {
			for (j = 0; j <= n; j++) {
				ret[i][j] = Matrix[i][j];
			}
		}
		return n + 1;
	}

	////////////
	public static boolean EigenValue(double[][] Matrix, int n, int LoopNu, int Erro, double[][] Ret) {
		int i = Matrix.length;
		if (i != n) {
			return false;
		}
		int j;
		int k;
		int t;
		int m;
		double[][] A = new double[n][n];
		double erro = Math.pow(0.1, Erro);
		double b;
		double c;
		double d;
		double g;
		double xy;
		double p;
		double q;
		double r;
		double x;
		double s;
		double e;
		double f;
		double z;
		double y;
		int loop1 = LoopNu;
		Hessenberg(Matrix, n, A);// 将方阵K1转化成上Hessenberg矩阵A
		m = n;
		while (m != 0) {
			t = m - 1;
			while (t > 0) {
				if (Math.abs(A[t][t - 1]) > erro * (Math.abs(A[t - 1][t - 1]) + Math.abs(A[t][t]))) {
					t -= 1;
				} else {
					break;
				}
			}
			if (t == m - 1) {
				Ret[m - 1][0] = A[m - 1][m - 1];
				Ret[m - 1][1] = 0;
				m -= 1;
				loop1 = LoopNu;

			} else if (t == m - 2) {
				b = -(A[m - 1][m - 1] + A[m - 2][m - 2]);
				c = A[m - 1][m - 1] * A[m - 2][m - 2] - A[m - 1][m - 2] * A[m - 2][m - 1];
				d = b * b - 4 * c;
				y = Math.pow(Math.abs(d), 0.5);
				if (d > 0) {
					xy = 1;
					if (b < 0) {
						xy = -1;
					}
					Ret[m - 1][0] = -(b + xy * y) / 2;
					Ret[m - 1][1] = 0;
					Ret[m - 2][0] = c / Ret[m - 1][0];
					Ret[m - 2][1] = 0;
				} else {
					Ret[m - 1][0] = -b / 2;
					Ret[m - 2][0] = Ret[m - 1][0];
					Ret[m - 1][1] = y / 2;
					Ret[m - 2][1] = -Ret[m - 1][1];
				}
				m -= 2;
				loop1 = LoopNu;
			} else {
				if (loop1 < 1) {
					return false;
				}
				loop1 -= 1;
				j = t + 2;
				while (j < m) {
					A[j][j - 2] = 0;
					j += 1;
				}
				j = t + 3;
				while (j < m) {
					A[j][j - 3] = 0;
					j += 1;
				}
				k = t;
				while (k < m - 1) {
					if (k != t) {
						p = A[k][k - 1];
						q = A[k + 1][k - 1];
						if (k != m - 2) {
							r = A[k + 2][k - 1];
						} else {
							r = 0;
						}
					} else {
						b = A[m - 1][m - 1];
						c = A[m - 2][m - 2];
						x = b + c;
						y = c * b - A[m - 2][m - 1] * A[m - 1][m - 2];
						p = A[t][t] * (A[t][t] - x) + A[t][t + 1] * A[t + 1][t] + y;
						q = A[t + 1][t] * (A[t][t] + A[t + 1][t + 1] - x);
						r = A[t + 1][t] * A[t + 2][t + 1];
					}
					if (p != 0 || q != 0 || r != 0) {
						if (p < 0) {
							xy = -1;
						} else {
							xy = 1;
						}
						s = xy * Math.pow(p * p + q * q + r * r, 0.5);
						if (k != t) {
							A[k][k - 1] = -s;
						}
						e = -q / s;
						f = -r / s;
						x = -p / s;
						y = -x - f * r / (p + s);
						g = e * r / (p + s);
						z = -x - e * q / (p + s);
						for (j = k; j <= m - 1; j++) {
							b = A[k][j];
							c = A[k + 1][j];
							p = x * b + e * c;
							q = e * b + y * c;
							r = f * b + g * c;
							if (k != m - 2) {
								b = A[k + 2][j];
								p += f * b;
								q += g * b;
								r += z * b;
								A[k + 2][j] = r;
							}
							A[k + 1][j] = q;
							A[k][j] = p;

						}
						j = k + 3;
						if (j >= m - 1) {
							j = m - 1;
						}
						for (i = t; i <= j; i++) {
							b = A[i][k];
							c = A[i][k + 1];
							p = x * b + e * c;
							q = e * b + y * c;
							r = f * b + g * c;
							if (k != m - 2) {
								b = A[i][k + 2];
								p += f * b;
								q += g * b;
								r += z * b;
								A[i][k + 2] = r;
							}
							A[i][k + 1] = q;
							A[i][k] = p;
						}
					}
					k += 1;
				}

			}

		}
		return true;
	}

	public static void main(String[] args)// 测试
	{
		double[][] TestMatrix = { { 1, 22, 34, 22 },

				{ 1, 11, 5, 21 }, { 0, 1, 5, 11 }, { 7, 2, 13, 19 } };
		double[][] TMatrix1 = { { 1, 2, 3 }, { 2, 1, 1 }, { 2, 2, 2 } };
		double[][] TMatrix2 = { { 1, 2 }, { 2, 3 } };
		double[][] TMatrix3 = {{3,-1},{-1,3}};
		double[][] TMatrix4 = {{-1,1,0},{-4,3,0},{1,0,2}};
		double [][] data = {
				{-2, 1, 1},
				{0, 2, 0},
				{-4, 1, 3}
		};
		//double[][] GetEV = new double[3][2];
		//EigenValue(TMatrix2, 2, 400, 4, GetEV);
		//double[][] GetEV = new double[2][2];
		//EigenValue(TMatrix3, 2, 400, 4, GetEV);
		//double[][] GetEV = new double[3][3];
		//EigenValue(TMatrix4, 3, 400, 4, GetEV);
		double[][] GetEV = new double [3][3];
		EigenValue(data, 3, 400, 4, GetEV);
		String Strr = new String("");
		for (int i = 0; i < GetEV.length; i++) {
			String str = String.valueOf(GetEV[i][0]);//
			Strr += str;
			Strr += " ";
			String str1 = String.valueOf(GetEV[i][1]);
			Strr += str1;
			Strr += "\n";

		}

		System.out.println(Strr);
		
		for(int i = 0;i < 3;i++) {
			for(int j = 0;j < 3;j++) {
				System.out.print(data[i][j] + "\t");
			}
			System.out.println();
		}
	}
}

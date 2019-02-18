package com.linear.matrix;



/*
(1) 初始化特征向量为对角阵V，即主对角线的元素都是1.其他元素为0。
(2) 在A的非主对角线元素中，找到绝对值最大元素 apq 。
(3) 用式(3.14)计算tan2j，求 cosj, sinj 及矩阵Upq .
(4) 用公式(1)-(4)求A1；用当前特征向量矩阵V乘以矩阵Upq得到当前的特征向量V。
(5) 若当前迭代前的矩阵A的非主对角线元素中最大值小于给定的阈值e时，停止计算；否则, 令A = A1 , 重复执行(2) ~ (5)。 停止计算时，得到特征值 li≈(A1) ij ，i,j= 1,2,…,n.以及特征向量V。
(6) 这一步可选。根据特征值的大小从大到小的顺序重新排列矩阵的特征值和特征向量。
 */

public class Jacobi {
	/*
	 * 	0.8535413421611255	-0.2806734853106512	0.438964203398587	
		-0.2084239238711719	-0.9560866921943779	-0.20605267522397422	
		0.4775213557317917	0.08438383525524479	-0.8745585876130241
	 */
	
	public static void main(String[] args) {
		double [][] data = {
				{3,-1},
				{-1,3}
		};
		double[][] pdblVects = new double[data.length][data.length];
		Jacobi.jacobi(data, 2, pdblVects, null, 0, 10000);
		Matrix m = new Matrix(2, 2, data);
		MatrixExe.printMatrix(m);
		System.out.println(".........................");
		double [] data2 = {3,-1,-1,3};
		double [] pdbl = new double[4];
		Jacobi.jacobi2(data2, 2, pdbl, null, 0, 100000);
		
		for(int i = 0;i < 4;i++) {
			System.out.println(data2[i]);
		}
	}
	/**
	 * 求对称矩阵特征值和特征向量
	 * @param pMatrix 矩阵
	 * @param nDim  矩阵阶层
	 * @param pdblVects 特征向量(按列存储)
	 * @param pdbEigenValues 特征值数组
	 * @param dbEps 精度要求
	 * @param nJt  控制最大迭代次数
	 */
	public static void jacobi(double [][] pMatrix, int nDim, double [][]pdblVects,double []pdbEigenValues, double dbEps, int nJt) {
		if(nDim <= 1) {
			pdbEigenValues[0] = pMatrix[0][0];
			return;
		}
		//1.初始化特征向量矩阵为对角阵
		for(int i = 0;i < nDim;i++) {
			for(int j = 0;j < nDim;j++) {
				if(i == j) {
					pdblVects[i][j] = 1;
				}else {
					pdblVects[i][j] = 0;
				}
			}
		}
		int Count = 0;//迭代次数
		while(true) {
			//2.求 p  q
			int p = 0,q = 1;
			double max = Math.abs(pMatrix[0][1]);
			for(int i = 0;i < nDim;i++) {
				for(int j = 0;j < nDim;j++) {
					if(i != j && Math.abs(pMatrix[i][j]) > max) {
						p = i;
						q = j;
						max = Math.abs(pMatrix[i][j]);
					}
				}
			}
			
			if(Count > nJt)//满足最大迭代次数
				break;
			if(max < dbEps)//精度符合要求
				break;
			//System.out.println("p:" + p + ",q=" + q + ",max=" + max + ",count=" + Count);
			Count++;
			//计算 tan2a sina cosa upq
			double dbapq = pMatrix[p][q];
			double dbapp = pMatrix[p][p];
			double dbaqq = pMatrix[q][q];
			
			double angle2 = Math.atan2(-2.0 * dbapq, dbaqq - dbapp);
			double angle = angle2 * 0.5;
			double dbSin = Math.sin(angle);
			double dbCos = Math.cos(angle);
			double dbSin2 = Math.sin(angle2);
			double dbCos2 = Math.cos(angle2);
			
			pMatrix[p][p] = dbapp * dbCos * dbCos + dbaqq * dbSin * dbSin +
					2.0 * dbapq * dbCos * dbSin;
			pMatrix[q][q] = dbapp * dbSin * dbSin + dbaqq * dbCos * dbCos -
					2.0 * dbapq * dbCos * dbSin;
			pMatrix[p][q] = pMatrix[q][p] = 0.5 * (dbaqq - dbapp) * dbSin2 + dbapq * dbCos2;
			
			for(int i = 0;i < nDim;i++) {//(2)
				if(i != p && i != q) {
					pMatrix[p][i] = dbCos * pMatrix[p][i] + dbSin * pMatrix[q][i]; 
					pMatrix[q][i] = -1.0 * dbSin * pMatrix[p][i] + dbCos * pMatrix[q][i];
				}
			}
			
			for(int i = 0;i < nDim;i++) {//(3)
				if(i != p && i != q) {
					pMatrix[i][p] = dbCos * pMatrix[i][p] + dbSin * pMatrix[i][q]; 
					pMatrix[i][q] = -1.0 * dbSin * pMatrix[i][p] + dbCos * pMatrix[i][q];
				}
			}
			
			for(int i = 0;i < nDim;i++) {//(4)
				if(i != p && i != q)
				for(int j = 0;j < nDim;j++) {
					if(j != p && j != q) {
						pMatrix[j][i] = pMatrix[i][j];
					}
				}
			}
			//计算特征向量
			//临时数组
			double [][] tempv = new double [nDim][nDim];
			//初始化
			for(int i = 0;i < nDim;i++) {
				for(int j = 0;j < nDim;j++) {
					if(i == j) {
						tempv[i][j] = 1;
					}else {
						tempv[i][j] = 0;
					}
				}
			}
			tempv[p][p] = dbCos;
			tempv[p][q] = -1.0 * dbSin;
			tempv[q][p] = dbSin;
			tempv[q][q] = dbCos;
			Matrix m1 = new Matrix(nDim, nDim, pdblVects);
			Matrix m2 = new Matrix(nDim, nDim, tempv);
			Matrix k = MatrixExe.multia(m1, m2);
			for(int i = 0;i < nDim;i++) {
				for(int j = 0;j < nDim;j++) {
					pdblVects[i][j] = k.get(i+1, j+1);
				}
			}
		}
		
	}
	
	public static void jacobi2(double []pMatrix,int nDim, double []pdblVects, double []pdbEigenValues, double dbEps,int nJt) {
		for(int i = 0; i < nDim; i ++)   
	    {     
	        pdblVects[i*nDim+i] = 1.0f;   
	        for(int j = 0; j < nDim; j ++)   
	        {   
	            if(i != j)     
	                pdblVects[i*nDim+j]=0.0f;   
	        }   
	    }   
	  
	    int nCount = 0;     //迭代次数  
	    while(true)  
	    {  
	        //在pMatrix的非对角线上找到最大元素  
	        double dbMax = Math.abs(pMatrix[1]);  
	        int nRow = 0;  
	        int nCol = 1;  
	        for (int i = 0; i < nDim; i ++)          //行  
	        {  
	            for (int j = 0; j < nDim; j ++)      //列  
	            {  
	                double d = Math.abs(pMatrix[i*nDim+j]);   
	  
	                if((i!=j) && (d> dbMax))   
	                {   
	                    dbMax = d;     
	                    nRow = i;     
	                    nCol = j;   
	                }   
	            }  
	        }  
	  
	        if(dbMax < dbEps)     //精度符合要求   
	            break;    
	  
	        if(nCount > nJt)       //迭代次数超过限制  
	            break;  
	  
	        nCount++;  
	  
	        double dbApp = pMatrix[nRow*nDim+nRow];  
	        double dbApq = pMatrix[nRow*nDim+nCol];  
	        double dbAqq = pMatrix[nCol*nDim+nCol];  
	  
	        //计算旋转角度  
	        double dbAngle = 0.5*Math.atan2(-2*dbApq,dbAqq-dbApp);  
	        double dbSinTheta = Math.sin(dbAngle);  
	        double dbCosTheta = Math.cos(dbAngle);  
	        double dbSin2Theta = Math.sin(2*dbAngle);  
	        double dbCos2Theta = Math.cos(2*dbAngle);  
	  
	        pMatrix[nRow*nDim+nRow] = dbApp*dbCosTheta*dbCosTheta +   
	            dbAqq*dbSinTheta*dbSinTheta + 2*dbApq*dbCosTheta*dbSinTheta;  
	        pMatrix[nCol*nDim+nCol] = dbApp*dbSinTheta*dbSinTheta +   
	            dbAqq*dbCosTheta*dbCosTheta - 2*dbApq*dbCosTheta*dbSinTheta;  
	        pMatrix[nRow*nDim+nCol] = 0.5*(dbAqq-dbApp)*dbSin2Theta + dbApq*dbCos2Theta;  
	        pMatrix[nCol*nDim+nRow] = pMatrix[nRow*nDim+nCol];  
	  
	        for(int i = 0; i < nDim; i ++)   
	        {   
	            if((i!=nCol) && (i!=nRow))   
	            {   
	                int u = i*nDim + nRow;  //p    
	                int w = i*nDim + nCol;  //q  
	                dbMax = pMatrix[u];   
	                pMatrix[u]= pMatrix[w]*dbSinTheta + dbMax*dbCosTheta;   
	                pMatrix[w]= pMatrix[w]*dbCosTheta - dbMax*dbSinTheta;   
	            }   
	        }   
	  
	        for (int j = 0; j < nDim; j ++)  
	        {  
	            if((j!=nCol) && (j!=nRow))   
	            {   
	                int u = nRow*nDim + j;  //p  
	                int w = nCol*nDim + j;  //q  
	                dbMax = pMatrix[u];   
	                pMatrix[u]= pMatrix[w]*dbSinTheta + dbMax*dbCosTheta;   
	                pMatrix[w]= pMatrix[w]*dbCosTheta - dbMax*dbSinTheta;   
	            }   
	        }  
	  
	        //计算特征向量  
	        for(int i = 0; i < nDim; i ++)   
	        {   
	            int u = i*nDim + nRow;      //p     
	            int w = i*nDim + nCol;      //q  
	            dbMax = pdblVects[u];   
	            pdblVects[u] = pdblVects[w]*dbSinTheta + dbMax*dbCosTheta;   
	            pdblVects[w] = pdblVects[w]*dbCosTheta - dbMax*dbSinTheta;   
	        }   
	  
	    }  
	}
}

















package com.linear.matrix;

/**
 * 3
 * 1/2
 * 1/3
 * -1/3
 * -5/6
 * 0
 * @author 任志江
 *
 */
public class Decimal {
	public static void main(String[] args) {
		Decimal d= new Decimal("0");
		Decimal d2= new Decimal("0.0");
		System.out.println(d.value);
		System.out.println(d.equals(d2));
	}
	private String value;
	/**
	 * value形式:  3    1/2   -1/3  -3    32.632
	 * @param value
	 */
	public Decimal(String value) {
		
		if(value.contains(".")) {
			int dindex = value.lastIndexOf(".");
			int ws = value.length() - 1 - dindex;
			value = value.replace(".", "");
			value += ("/" + (int)Math.pow(10, ws));
		}
		if(!value.contains("/"))
			value = value + "/1";
		this.value = value;
		this.zli();
	}
	/**
	 * 加法
	 * @param b
	 */
	public Decimal add(Decimal b) {
		int a_fm = this.getfm();
		int a_fz = this.getfz();
		int b_fm = b.getfm();
		int b_fz = b.getfz();
		if(a_fm == b_fm) {
			this.value = (a_fz + b_fz) + "/" + a_fm;
		}else {
			this.value = (a_fz*b_fm + b_fz*a_fm) + "/" + (a_fm * b_fm);
		}
		zli();
		return this;
	}
	/**
	 * 乘法
	 * @param b
	 */
	public Decimal multiply(Decimal b) {
		int a_fm = this.getfm();
		int a_fz = this.getfz();
		int b_fm = b.getfm();
		int b_fz = b.getfz();
		this.value = (a_fz*b_fz) + "/" + (a_fm * b_fm);
		zli();
		return this;
	}
	/**
	 * 减法
	 * this-b
	 * @param b
	 */
	public Decimal subtract(Decimal b) {
		int a_fm = this.getfm();
		int a_fz = this.getfz();
		int b_fm = b.getfm();
		int b_fz = b.getfz();
		if(a_fm == b_fm) {
			this.value = (a_fz - b_fz) + "/" + a_fm;
		}else {
			this.value = (a_fz*b_fm - b_fz*a_fm) + "/" + (a_fm * b_fm);
		}
		zli();
		return this;
	}
	/**
	 * 除法
	 * @param b
	 */
	public Decimal divide(Decimal b) {
		int b_fm = b.getfm();
		int b_fz = b.getfz();
		this.multiply(new Decimal(b_fm + "/" + b_fz));
		return this;
	}
	public Decimal copy() {
		return new Decimal(this.value);
	}
	private void zli() {
		int a_fz = this.getfz();
		int a_fm = this.getfm();
		if(a_fz % a_fm == 0) {
			this.value = a_fz/a_fm+"/1";
		}else {
			int gy = 0;
			while((gy = gongyue(a_fz, a_fm)) != 1) {
				a_fz = a_fz / gy;
				a_fm = a_fm / gy;
			}
			if(a_fm < 0) {
				a_fz*=-1;
				a_fm*=-1;
			}
			this.value = a_fz + "/" + a_fm;
		}
	}
	int gongyue(int m,int n)/*--*/
	{
	 int r;
	 if(m==n) return m;
	 else
		 while((r=m%n)!=0)
		 {
			 m=n;
			 n=r;
		 }
	  return n;
	 }
	private int getfz() {
		String [] arr = value.split("/");
		return Integer.parseInt(arr[0]);
	}
	private int getfm() {
		String [] arr = value.split("/");
		return Integer.parseInt(arr[1]);
	}

	@Override
	public String toString() {
		if(this.value.endsWith("/1")) {
			return this.getfz()+"";
		}else {
			return value;
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Decimal other = (Decimal) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
	
}

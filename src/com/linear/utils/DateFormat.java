package com.linear.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
	public static String toString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
}

package com.test.springboot.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat dateTimeMergeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static String formatDateTime(Date date) {
		if (null != date) {
			return dateTimeFormat.format(date);
		}
		return "";
	}
	
	public static String formatDateTimeMerge(Date date) {
		if (null != date) {
			return dateTimeMergeFormat.format(date);
		}
		return "";
	}
	
	
	public static int getLastDayOfMonth(Date date) {
	    Calendar calender = Calendar.getInstance();
	    calender.setTime(date);
	    int num = calender.getActualMaximum(Calendar.DAY_OF_MONTH);
	    return num;
	}

	public static Date getLastMonth(){
	    Calendar calendar1 = Calendar.getInstance();
	    calendar1.setTime(new Date());
	    calendar1.set(Calendar.MONTH, calendar1.get(Calendar.MONTH) - 1);
	    return calendar1.getTime();

	}
	
}

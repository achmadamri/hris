package com.gwt.hris.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
	public int countDaysBetween(Date start, Date end) {
		long MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;

		Calendar startCal = GregorianCalendar.getInstance();
		startCal.setTime(start);
		startCal.set(Calendar.HOUR_OF_DAY, 0);
		startCal.set(Calendar.MINUTE, 0);
		startCal.set(Calendar.SECOND, 0);
		long startTime = startCal.getTimeInMillis();

		Calendar endCal = GregorianCalendar.getInstance();
		endCal.setTime(end);
		endCal.set(Calendar.HOUR_OF_DAY, 0);
		endCal.set(Calendar.MINUTE, 0);
		endCal.set(Calendar.SECOND, 0);
		long endTime = endCal.getTimeInMillis();

		return Long.valueOf((endTime - startTime) / MILLISECONDS_IN_DAY).intValue();
	}

	public static void main(String[] args) {
		DateUtil dateUtils = new DateUtil();

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
//		cal1.add(Calendar.DATE, -1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(new Date());
		cal2.add(Calendar.DATE, 0);

		System.out.println(dateUtils.countDaysBetween(cal1.getTime(), cal2.getTime()));
	}
}
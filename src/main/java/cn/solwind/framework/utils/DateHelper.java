package cn.solwind.framework.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
* @author Zhouluning
* @version 创建时间：2019-04-23
*
* 日期工具类
*/

public class DateHelper {

	/**
	 * 获取日期所在年份的周
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getWeekOfYear(Date date) {
		
		Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
		Integer week = new Integer("" +localDate.get(IsoFields.WEEK_BASED_YEAR) + localDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR));
		
		return week;
	}
	
	/**
	 * 获得日期所在年份的月
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getMonthOfYear(Date date) {
		return new Integer(DateFormatUtils.format(date, "yyyyMM"));
	}
	
	/**
	 * 获取当周第一天
	 * 周一为首日的情况下
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		
		Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTime();
	}
	
	/**
	 * 获取当周最后一天
	 * 周一为首日的情况下
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {

		Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        
		return c.getTime();
	}
	
	/**
	 * 获取当月第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTime();
	}
	
	/**
	 * 获取当月最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar c = new GregorianCalendar();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        return c.getTime();
	}
	
	/**
	 * 获取两个日期间的日期列表(包含起始、截止两个日期)
	 * 参数中时分秒会被清空
	 * 
	 * @param fromDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> getBetweenDate(Date fromDate,Date endDate) {
		
		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(fromDate);
		cFrom.set(Calendar.HOUR, 0);
		cFrom.set(Calendar.MINUTE, 0);
		cFrom.set(Calendar.SECOND, 0);
		
		Calendar cEnd = Calendar.getInstance();
		cEnd.setTime(endDate);
		cEnd.set(Calendar.HOUR, 0);
		cEnd.set(Calendar.MINUTE, 0);
		cEnd.set(Calendar.SECOND, 0);
		
		List<Date> listReturn = new ArrayList<Date>();
		
		Calendar cTmp = Calendar.getInstance();
		cTmp.setTime(cFrom.getTime());
		while (cTmp.getTimeInMillis() < cEnd.getTimeInMillis()) {
			listReturn.add(cTmp.getTime());
			cTmp.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		listReturn.add(cEnd.getTime());
		
		return listReturn;
	}
	
	/**
	 * 获取日期所在周的所有日期
	 * 周一为首日的情况下
	 * 
	 * @param date
	 * @return
	 */
	public static List<Date> getDaysOfWeek(Date date) {
		return getBetweenDate(getFirstDayOfWeek(date), getLastDayOfWeek(date));
	}
	
	/**
	 * 获取日期所在月的所有日期
	 * 
	 * @param date
	 * @return
	 */
	public static List<Date> getDaysOfMonth(Date date) {
		return getBetweenDate(getFirstDayOfMonth(date), getLastDayOfMonth(date));
	}
}

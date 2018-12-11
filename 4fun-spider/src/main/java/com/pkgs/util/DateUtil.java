package com.pkgs.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * 
 * @author cs12110 at 2018年12月10日下午10:18:46
 *
 */
public class DateUtil {

	private static final String DEF_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取当前时间:yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 */
	public static String getTime() {
		return format(new Date(), DEF_FORMAT);
	}

	/**
	 * 格式化日期
	 * 
	 * @param timestamp
	 *            时间戳
	 * @return String
	 */
	public static String format(long timestamp) {
		Date date = new Date(timestamp);
		return format(date, DEF_FORMAT);
	}

	/**
	 * 格式化日期格式
	 * 
	 * @param date
	 *            date对象
	 * @param format
	 *            时间格式
	 * @return String
	 */
	public static String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
}

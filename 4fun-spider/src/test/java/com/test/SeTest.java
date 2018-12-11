package com.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import com.pkgs.util.DateUtil;

public class SeTest {
	@Test
	public void test() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = sdf.parse("2016-11-04 22:27:00");

		System.out.println(date.getTime());
		System.out.println(date.getTime() - 1424072303);

		System.out.println(DateUtil.format(new Date(1424072303), "yyyy-MM-dd HH:mm:ss"));
	}
}

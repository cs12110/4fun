package com.pkgs.util;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 消息队列工具类
 * 
 *
 * <p>
 *
 * @author cs12110 2018年12月11日
 * @see
 * @since 1.0
 */
public class MqUtil {

	private static Queue<Object> mq = new LinkedBlockingDeque<>();

	public static void offer(Object element) {
		mq.offer(element);
	}

	public static Object poll() {
		return mq.poll();
	}

}

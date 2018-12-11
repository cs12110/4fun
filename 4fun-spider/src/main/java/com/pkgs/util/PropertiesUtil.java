package com.pkgs.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class PropertiesUtil {

	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	private static Map<String, Object> cache = new HashMap<String, Object>();

	private static final String PRO_FILE_NAME = "sys.properties";

	static {
		init();
	}

	/**
	 * 初始化参数
	 */
	private static void init() {
		InputStream stream = PropertiesUtil.class.getResourceAsStream("/" + PRO_FILE_NAME);
		// 被打包出去了
		if (stream == null) {
			try {
				File file = new File("config/" + PRO_FILE_NAME);
				stream = new FileInputStream(file);
				logger.info("using: ", file.getAbsolutePath());
			} catch (Exception e) {
				logger.error("{}", e);
			}
		}
		if (null != stream) {
			try {
				Properties pro = new Properties();
				pro.load(stream);
				Set<Entry<Object, Object>> entrySet = pro.entrySet();
				for (Entry<Object, Object> each : entrySet) {
					cache.put(String.valueOf(each.getKey()), each.getValue());
				}
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			logger.error("We can't load the sys.properties ,please check");
			System.exit(1);
		}
		logger.info("sys:{}", JSON.toJSONString(cache, true));
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(String key) {
		return (T) cache.get(key);
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(String key, T def) {
		Object value = cache.get(key);
		return null == value ? def : (T) value;
	}

	public static Map<String, Object> getValueMap() {
		return cache;
	}
}

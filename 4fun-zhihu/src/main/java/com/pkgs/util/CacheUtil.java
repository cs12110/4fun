package com.pkgs.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地缓存工具类
 *
 * @author cs12110 create at 2019/5/10 13:51
 * @version 1.0.0
 */
public class CacheUtil {

    private static Map<String, Object> cache = new ConcurrentHashMap<>();


    public static void put(String key, Object value) {
        cache.put(key, value);
    }

    public static Object get(String key) {
        return cache.get(key);
    }


    public static boolean containKey(String key) {
        return cache.containsKey(key);
    }

    public static void remove(String key) {
        cache.remove(key);
    }

}
package com.pkgs.util;

/**
 * 字符串工具类
 * <p/>
 *
 * @author cs12110 created at: 2019/1/31 10:43
 * <p>
 * since: 1.0.0
 */
public class StrUtil {

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str.trim());
    }


    /**
     * 去除字符, 如: abc,去除a,返回bc
     *
     * @param str     字符串
     * @param abandon 字符
     * @return String
     */
    public static String abandonChar(String str, char abandon) {
        if (null == str) {
            return null;
        }
        StringBuilder builder = new StringBuilder(str.length());
        for (char ch : str.toCharArray()) {
            if (ch != abandon) {
                builder.append(ch);
            }
        }
        return builder.toString();
    }
}

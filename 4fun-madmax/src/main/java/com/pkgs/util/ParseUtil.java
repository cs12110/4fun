package com.pkgs.util;

/**
 * <p/>
 *
 * @author cs12110 created at: 2019/2/13 15:55
 * <p>
 * since: 1.0.0
 */
public class ParseUtil {

    /**
     * 列名称转换成字段名称
     *
     * @param columnName 列名称
     * @return String
     */
    public static String columnNameToFieldName(String columnName) {
        StringBuilder builder = new StringBuilder();
        char[] arr = columnName.toCharArray();
        for (int i = 0, len = arr.length; i < len; i++) {
            if (arr[i] == '_') {
                if (++i < len) {
                    builder.append((char) (arr[i] - 32));
                }
            } else {
                builder.append(arr[i]);
            }
        }
        return builder.toString();
    }

    /**
     * 表名称转换成实体类名称
     *
     * @param tableName    表名称
     * @param tableSuffix  表后缀,例如: _t
     * @param entitySuffix 实体类后缀,例如: Entity
     * @return String
     */
    public static String tableNameToEntityName(String tableName, String tableSuffix, String entitySuffix) {
        if (null != tableSuffix) {
            tableName = tableName.replaceAll(tableSuffix, "");
        }

        StringBuilder builder = new StringBuilder();
        char[] arr = tableName.toCharArray();
        for (int i = 0, len = arr.length; i < len; i++) {
            if (arr[i] == '_') {
                if (++i < len) {
                    builder.append((char) (arr[i] - 32));
                }
            } else {
                if (i == 0) {
                    builder.append(((char) (arr[i] - 32)));
                } else {
                    builder.append(arr[i]);
                }
            }
        }
        if (null != entitySuffix && !"".equals(entitySuffix.trim())) {
            builder.append(entitySuffix);
        }
        return builder.toString();
    }
}

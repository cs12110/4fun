package com.pkgs.util;

import com.pkgs.entity.ColumnInfoEntity;
import com.pkgs.enums.MetaEnum;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据库信息工具类
 * <p>
 * <p/>
 *
 * @author cs12110 created at: 2019/2/13 14:43
 * <p>
 * since: 1.0.0
 */
public class DbInfoUtil {

    /**
     * 获取数据库表名称
     *
     * @param connection 连接
     * @param dbName     数据库名称
     * @return Set
     */
    public static Set<String> getTables(Connection connection, String dbName) {
        Set<String> tables = new HashSet<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(dbName, "%", null, "%");
            while (resultSet.next()) {
                String tableName = resultSet.getString(MetaEnum.TABLE_NAME.toString());
                tables.add(tableName);
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tables;
    }

    /**
     * 获取表字段信息
     *
     * @param connection connection
     * @param dbName     数据库名称
     * @param tableName  表名称
     * @return Set
     */
    public static Set<ColumnInfoEntity> getColumns(Connection connection, String dbName, String tableName) {
        Set<ColumnInfoEntity> columns = new HashSet<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(dbName, "%", tableName, "%");
            Set<String> primaryKeys = getPrimaryKeys(connection, dbName, tableName);

            while (resultSet.next()) {
                ColumnInfoEntity entity = new ColumnInfoEntity();
                entity.setName(resultSet.getString(MetaEnum.COLUMN_NAME.toString()));
                entity.setType(resultSet.getString(MetaEnum.TYPE_NAME.toString()));
                entity.setRemark(resultSet.getString(MetaEnum.REMARKS.toString()));
                entity.setLen(resultSet.getInt(MetaEnum.CHAR_OCTET_LENGTH.toString()));
                entity.setAutoIncrement(resultSet.getBoolean(MetaEnum.IS_AUTOINCREMENT.toString()));
                entity.setNullable(resultSet.getBoolean(MetaEnum.IS_NULLABLE.toString()));

                if (primaryKeys.contains(entity.getName())) {
                    entity.setPrimaryKey(true);
                }

                columns.add(entity);
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return columns;
    }

    /**
     * 获取数据表的primary key
     *
     * @param connection connection
     * @param dbName     数据库名称
     * @param tableName  表名称
     * @return set
     */
    public static Set<String> getPrimaryKeys(Connection connection, String dbName, String tableName) {
        Set<String> primaryKeys = new HashSet<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getPrimaryKeys(dbName, "", tableName);
            while (resultSet.next()) {
                primaryKeys.add(resultSet.getString(MetaEnum.COLUMN_NAME.toString()));
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return primaryKeys;
    }
}

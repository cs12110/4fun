package com.pkgs.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Jdbc工具类
 *
 * @author cs12110 at 2018年12月10日下午10:10:22
 */
public class JdbcUtil {

    private static Logger logger = LoggerFactory.getLogger(JdbcUtil.class);
    private static ComboPooledDataSource dataSource;

    static {
        init();
    }

    /**
     * init method
     */
    private static void init() {
        try {
            dataSource = new ComboPooledDataSource();
            dataSource.setJdbcUrl(PropertiesUtil.get("db.url"));
            dataSource.setUser(PropertiesUtil.get("db.user"));
            dataSource.setPassword(PropertiesUtil.get("db.password"));
            dataSource.setDriverClass(PropertiesUtil.get("db.driver"));

            dataSource.setInitialPoolSize(2);
            dataSource.setMinPoolSize(2);
            dataSource.setMaxPoolSize(10);
            dataSource.setMaxStatements(50);
            dataSource.setMaxIdleTime(60);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取数据库连接
     *
     * @return Connection
     */
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("{}", e);
            throw new RuntimeException("get connection from pool is failure", e);
        }
    }

    /**
     * 关闭连接
     *
     * @param conn connection of jdbc
     */
    public static void close(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭声明
     *
     * @param stm statement
     */
    public static void close(Statement stm) {
        try {
            if (stm != null) {
                stm.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭结果集
     *
     * @param result ResultSet
     */
    public static void close(ResultSet result) {
        try {
            if (result != null) {
                result.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

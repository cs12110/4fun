package com.test;

import com.pkgs.entity.ColumnInfoEntity;
import com.pkgs.util.DbInfoUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Set;

/**
 * <p/>
 *
 * @author cs12110 created at: 2019/2/13 15:51
 * <p>
 * since: 1.0.0
 */
public class DbInfoTest {
    @Test
    public void test() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://47.98.104.252:3306/4fun_db?useUnicode=true&characterEncoding=UTF-8&useSSL=false",
                    "root",
                    "Root@3306");

            Set<String> tableList = DbInfoUtil.getTables(connection, "4fun_db");
            System.out.println(tableList);

            Set<ColumnInfoEntity> columns = DbInfoUtil.getColumns(connection, "4fun_db", "book_tag_t");
            System.out.println(columns);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.pkgs.util;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Sql session util
 * <p/>
 *
 * @author cs12110 created at: 2019/1/11 9:34
 * <p>
 * since: 1.0.0
 */
public class SqlSessionUtil {

    private static SqlSessionFactory sessionFactory;

    private static volatile boolean isInit = false;


    /**
     * 初始化SQLSessionFactory
     */
    private static void init() {
        try {
            InputStream stream = SqlSessionUtil.class.getResourceAsStream("/mybatis-config.xml");
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            sessionFactory = sqlSessionFactoryBuilder.build(stream);
            stream.close();
            isInit = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open
     *
     * @return SqlSession
     */
    public static SqlSession openSession() {
        if (!isInit) {
            synchronized (SqlSessionUtil.class) {
                if (!isInit) {
                    init();
                }
            }
        }
        return sessionFactory.openSession();
    }

    /**
     * close
     *
     * @param session {@link SqlSession}
     */
    public static void closeSession(SqlSession session) {
        if (null != session) {
            session.close();
        }
    }

}

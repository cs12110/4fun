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

    private static boolean isInit = false;


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

    public static class ProxyMapper implements InvocationHandler {

        @SuppressWarnings("unchecked")
        public static <T> T wrapper(Class<T> mapperClass) {
            Class[] interfaces = {mapperClass};
            return (T) Proxy.newProxyInstance(mapperClass.getClassLoader(), interfaces, new ProxyMapper(mapperClass));
        }

        private Class<?> mapper;

        ProxyMapper(Class<?> mapper) {
            this.mapper = mapper;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {
            SqlSession session = SqlSessionUtil.openSession();
            Object result = null;
            try {
                result = method.invoke(session.getMapper(this.mapper), args);
                session.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
            return result;
        }
    }
}

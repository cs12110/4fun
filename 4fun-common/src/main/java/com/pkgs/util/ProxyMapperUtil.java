package com.pkgs.util;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * TODO: ProxyMapper 工具类
 * <p>
 *
 * @author cs12110 create at: 2019/1/12 10:49
 * Since: 1.0.0
 */
public class ProxyMapperUtil implements InvocationHandler {

    /**
     * 构建mapper的代理类
     *
     * @param mapperClass mapper接口,必须为接口的class
     * @param <T>         T
     * @return T
     */
    @SuppressWarnings("unchecked")
    public static <T> T wrapper(Class<T> mapperClass) {
        if (!mapperClass.isInterface()) {
            throw new IllegalArgumentException("mapper class must be an interface");
        }

        Class[] interfaces = {mapperClass};
        return (T) Proxy.newProxyInstance(mapperClass.getClassLoader(), interfaces, new ProxyMapperUtil(mapperClass));
    }

    private Class<?> mapper;

    private ProxyMapperUtil(Class<?> mapper) {
        this.mapper = mapper;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        SqlSession session = SqlSessionUtil.openSession();
        Object sessionMapper = session.getMapper(this.mapper);
        Object result = null;
        try {
            result = method.invoke(sessionMapper, args);

            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SqlSessionUtil.closeSession(session);
        }
        return result;
    }
}

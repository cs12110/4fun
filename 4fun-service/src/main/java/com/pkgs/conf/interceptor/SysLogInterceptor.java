package com.pkgs.conf.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 日志拦截器
 * <p/>
 *
 * @author cs12110 created at: 2019/1/7 14:09
 * <p>
 * since: 1.0.0
 */
@Aspect
@Component
public class SysLogInterceptor {

    private static Logger logger = LoggerFactory.getLogger(SysLogInterceptor.class);

    /**
     * 拦截controller下面的所有请求
     */
    @Pointcut("execution(* com.pkgs.ctrl..*(..))")
    public void aop() {

    }


    @Around("aop()")
    public Object handle(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();

        Method method = signature.getMethod();
        Class<?> targetClazz = method.getDeclaringClass();

        Logger targetLogger = LoggerFactory.getLogger(targetClazz);

        Object result = null;
        try {
            long start = System.currentTimeMillis();
            result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            targetLogger.info("{} spend:{}", method.getName(), (end - start));
        } catch (Throwable e) {
            logger.error("{}", e);
        }
        return result;
    }
}

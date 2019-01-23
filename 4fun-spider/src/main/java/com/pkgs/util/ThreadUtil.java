package com.pkgs.util;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程工厂
 * <p/>
 *
 * @author cs12110 created at: 2019/1/19 8:50
 * <p>
 * since: 1.0.0
 */
public class ThreadUtil {

    /**
     * 创建系统任务启动线程
     *
     * @return ExecutorService
     */
    public static ExecutorService buildTaskPoolExecutor() {
        int coreSize = 3;
        int maxSize = 3;
        int keepAlie = 0;


        return new ThreadPoolExecutor(
                coreSize,
                maxSize,
                keepAlie,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(),
                buildFactory("startup"));
    }

    /**
     * 获取爬虫执行线程池
     *
     * @param coreSize coreSize
     * @return ExecutorService
     */
    public static ExecutorService buildSpiderExecutor(int coreSize) {
        int keepAliveTime = 0;
        String prefixName = "spider";

        return new ThreadPoolExecutor(
                coreSize,
                coreSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(),
                ThreadUtil.buildFactory(prefixName));
    }


    /**
     * 创建线程工厂
     *
     * @param prefixName 线程名称前缀
     * @return ThreadFactory
     */
    public static ThreadFactory buildFactory(String prefixName) {
        return new CustomerThreadFactory(prefixName);
    }


    /**
     * 自定义线程工厂
     */
    static class CustomerThreadFactory implements ThreadFactory {
        /**
         * 前缀名称
         */
        private String prefixName;

        /**
         * 线程计数器
         */
        private AtomicInteger threadCounter = new AtomicInteger(1);

        /**
         * 线程前缀名称
         *
         * @param prefixName 前缀
         */
        CustomerThreadFactory(String prefixName) {
            this.prefixName = prefixName;
        }

        @Override
        public Thread newThread(Runnable r) {
            String threadName = prefixName + "-thread:" + threadCounter.getAndIncrement();
            return new Thread(r, threadName);
        }
    }
}

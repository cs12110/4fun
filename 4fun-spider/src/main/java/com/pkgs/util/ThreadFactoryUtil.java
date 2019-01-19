package com.pkgs.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程工厂
 * <p/>
 *
 * @author cs12110 created at: 2019/1/19 8:50
 * <p>
 * since: 1.0.0
 */
public class ThreadFactoryUtil {

    /**
     * 自定义线程工厂
     */
    public static class CustomerThreadFactory implements ThreadFactory {
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

    public static ThreadFactory buildCustomerThreadFactory(String prefixName) {
        return new CustomerThreadFactory(prefixName);
    }
    //
    // /**
    //  * 前缀名称
    //  */
    // private String prefixName;
    //
    // /**
    //  * 线程计数器
    //  */
    // private AtomicInteger threadCounter = new AtomicInteger(1);
    //
    // /**
    //  * 线程前缀名称
    //  *
    //  * @param prefixName 前缀
    //  */
    // public ThreadFactoryUtil(String prefixName) {
    //     this.prefixName = prefixName;
    // }
    //
    // @Override
    // public Thread newThread(Runnable r) {
    //     String threadName = prefixName + "-thread:" + threadCounter.getAndIncrement();
    //     return new Thread(r, threadName);
    // }
}

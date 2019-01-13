package com.test.mq;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * TODO:
 *
 * @author cs12110 create at: 2019/1/13 20:38
 * Since: 1.0.0
 */
public class MqUtil {

    private static boolean isFinish = false;

    private static BlockingQueue<String> queue = new LinkedBlockingDeque<>();

    public static boolean isIsFinish() {
        return isFinish;
    }

    public static void setIsFinish(boolean isFinish) {
        MqUtil.isFinish = isFinish;
    }


    public static void put(String line) {
        try {
            queue.put(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String poll() {
        return queue.poll();
    }
}

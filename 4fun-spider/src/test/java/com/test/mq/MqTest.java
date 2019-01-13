package com.test.mq;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO:
 *
 * @author cs12110 create at: 2019/1/13 20:42
 * Since: 1.0.0
 */
public class MqTest {

    public static void main(String[] args) {
        new Thread(new Provider()).start();
        new Thread(new Customer("t1")).start();
        new Thread(new Customer("t2")).start();

    }


    static class Provider implements Runnable {

        @Override
        public void run() {
            int index = 0;
            while (index++ < 10) {
                MqUtil.put("No" + index);
            }

            MqUtil.setIsFinish(true);
        }
    }

    static class Customer implements Runnable {

        private String threadName;

        public Customer(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            while (true) {
                String poll = MqUtil.poll();
                if (poll == null && MqUtil.isIsFinish()) {
                    break;
                }
                if (poll != null) {
                    System.out.println(getTime() + " - " + threadName + ": " + poll);
                }
            }

            System.out.println(getTime() + " - " + threadName + ": it's all done");
        }
    }

    public static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(new Date());
    }
}

package com.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class SeTest {


    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {

        for (int index = 0; index < 5; index++) {
            new Thread(new MyRun(index)).start();
        }
    }


    static class MyRun implements Runnable {
        private int index;

        public MyRun(int index) {
            this.index = index;
        }

        @Override
        public void run() {

            try {
                boolean tryLock = lock.tryLock(8, TimeUnit.SECONDS);

                if (tryLock) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = sdf.format(new Date());
                    System.out.println(time + " " + this + "[" + index + "]" + " running");

                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        // private String getTime() {
        //     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //
        //     return sdf.format(new Date());
        // }
    }


}

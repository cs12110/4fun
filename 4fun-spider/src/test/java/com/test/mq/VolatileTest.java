package com.test.mq;

/**
 * TODO:
 *
 * @author cs12110 create at: 2019/1/13 20:54
 * Since: 1.0.0
 */
public class VolatileTest {

    private volatile boolean isStopNow = false;

    public static void main(String[] args) {

        VolatileTest vt = new VolatileTest();

        new Thread(new MyRun(vt)).start();


        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("set to stop");
        vt.isStopNow = true;
        System.out.println("set to stop is done");
    }

    static class MyRun implements Runnable {
        private VolatileTest vt;

        public MyRun(VolatileTest vt) {
            this.vt = vt;
        }

        @Override
        public void run() {
            System.out.println("it is start");
            while (true) {
                if (vt.isStopNow) {
                    break;
                }
            }
            System.out.println("thread all is done");
        }
    }

}

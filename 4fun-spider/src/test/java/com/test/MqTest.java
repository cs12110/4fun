package com.test;

import com.pkgs.util.MqUtil;

public class MqTest {

	public static void main(String[] args) {
		new Thread(new Consumer("c1")).start();
		new Thread(new Consumer("c2")).start();
		new Thread(new Consumer("c3")).start();
		new Thread(new Provider()).start();
	}

	static class Provider implements Runnable {
		@Override
		public void run() {
			long num = 0;
			while (true) {
				num++;
				MqUtil.offer(num);
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
				}
			}
		}
	}

	static class Consumer implements Runnable {

		private String threadName;

		public Consumer(String threadName) {
			super();
			this.threadName = threadName;
		}

		@Override
		public void run() {
			while (true) {
				Object value = MqUtil.poll();
				if (value == null) {
					try {
						Thread.sleep(2000);
					} catch (Exception e) {
					}
				} else {
					System.out.println(threadName + ": " + value);
				}
			}
		}
	}
}

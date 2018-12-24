package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

public class SeTest {
	private static CountDownLatch latch = new CountDownLatch(5);
	@Test
	public void test() throws Exception {
		// ExecutorService pool = Executors.newFixedThreadPool(5);
		//
		// int j = 0;
		// while (j < 2) {
		// latch = new CountDownLatch(3);
		// System.out.println(j + ":" + DateUtil.getTime());
		// pool.submit(new MyRun(1000));
		// pool.submit(new MyRun(2000));
		// pool.submit(new MyRun(3000));
		//
		// try {
		// latch.await();
		// } catch (Exception e) {
		// }
		// System.out.println(j + ":" + DateUtil.getTime());
		// j++;
		// }

		// 进行三个步进的划分

		List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");

		int threadNum = 3;
		int size = list.size();
		int limit = size % threadNum == 0 ? size / threadNum : size / threadNum + 1;
		for (int index = 0; index < limit; index++) {
			int start = index * threadNum;
			int end = start + threadNum > size ? size : start + threadNum;

			for (; start < end; start++) {
				System.out.println(list.get(start));
			}
			System.out.println();
			System.out.println();
		}
	}

	static class MyRun implements Runnable {

		private long sleep;

		public MyRun(long sleep) {
			super();
			this.sleep = sleep;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(sleep);

			} catch (Exception e) {
			}
			latch.countDown();
		}

	}
}

package com.xicheng.concurrent.chapter02.code.code230;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description
 *
 * @author liubin52
 * @date 2019-08-12 12:56:16
 */
public class Counter {

	private static AtomicInteger atomicInteger = new AtomicInteger(0);

	private static int num = 0;

	private static final int THREAD_CNT = 100;

	private static final Logger LOGGER = LoggerFactory.getLogger(Counter.class);

	/**
	 * 使用CAS实现线程安全计数器
	 */
	private static void safeCount() {
		for (; ; ) {
			int i = atomicInteger.get();
			boolean result = atomicInteger.compareAndSet(i, ++i);
			if (result) {
				break;
			}
		}
	}

	/**
	 * 非线程安全的计数器
	 */
	private static void count() {
		num++;
	}

	public static void main(String[] args) {
		List<Thread> threadList = new ArrayList<>(THREAD_CNT);
		for (int i = 0; i < THREAD_CNT; i++) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 10000; j++) {
						safeCount();
						count();
					}
				}
			});
			threadList.add(thread);
		}
		// 开启所有线程
		for (Thread thread : threadList) {
			thread.start();
		}
		// 等待所有线程完成
		for (Thread thread : threadList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		LOGGER.info("atomicInteger: {}", atomicInteger.get());
		LOGGER.info("num: {}", num);
	}
}

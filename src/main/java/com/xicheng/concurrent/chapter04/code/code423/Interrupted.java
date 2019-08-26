package com.xicheng.concurrent.chapter04.code.code423;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author liubin52
 * @date 2019-08-16 10:02:14
 */
public class Interrupted {

	public static final Logger LOGGER = LoggerFactory.getLogger(Interrupted.class);

	public static void main(String[] args) throws Exception {
		// 不停尝试睡眠的线程
		Thread sleepRunner = new Thread(new SleepRunner(), "SleepRunner");
		sleepRunner.setDaemon(true);
		// 不停工作的线程
		Thread busyRunner = new Thread(new BusyRunner(), "BusyRunner");
		busyRunner.setDaemon(true);
		// 休眠5秒，让两个线程充分执行
		TimeUnit.SECONDS.sleep(5);

		sleepRunner.interrupt();
		busyRunner.interrupt();

		LOGGER.info("SleepRunner state: {}", sleepRunner.isInterrupted());
		LOGGER.info("BusyRunner state: {}", busyRunner.isInterrupted());

		// 防止线程突然退出
		TimeUnit.SECONDS.sleep(2);
	}

	static class SleepRunner implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static class BusyRunner implements Runnable {
		@Override
		public void run() {
			while (true) {

			}
		}
	}
}

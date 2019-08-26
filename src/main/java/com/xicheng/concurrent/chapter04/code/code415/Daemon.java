package com.xicheng.concurrent.chapter04.code.code415;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * main线程结束时，守护线程会跟着结束，不会进入finally
 *
 * @author liubin52
 * @date 2019-08-16 09:17:33
 */
public class Daemon {

	public static final Logger LOGGER = LoggerFactory.getLogger(Daemon.class);

	public static void main(String[] args) {
		Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
		thread.setDaemon(true);
		thread.start();
		LOGGER.info("Main thread finished");
	}

	static class DaemonRunner implements Runnable {
		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				LOGGER.info("Collect your resources...");
			}
		}
	}
}

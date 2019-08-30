package com.xicheng.concurrent.book.chapter04.code.code424;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * description
 *
 * @author liubin52
 * @date 2019-08-16 10:21:09
 */
public class Deprecated {

	public static final Logger LOGGER = LoggerFactory.getLogger(Deprecated.class);

	public static void main(String[] args) {
		Thread thread = new Thread(new PrintRunner(), "PrintRunner");
		thread.setDaemon(true);
		thread.start();

	}

	static class PrintRunner implements Runnable {
		@Override
		public void run() {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			while (true) {
				LOGGER.info("Thread: {} is running, time: {}", Thread.currentThread().getName(),
						simpleDateFormat.format(new Date()));
			}
		}
	}
}

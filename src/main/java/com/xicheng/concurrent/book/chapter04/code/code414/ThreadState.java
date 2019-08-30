package com.xicheng.concurrent.book.chapter04.code.code414;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 需要先使用jps查看当前线程的pid，然后使用jstack pid查看各线程状态
 *
 * @author liubin52
 * @date 2019-08-15 14:31:21
 */
public class ThreadState {

	public static final Logger LOGGER = LoggerFactory.getLogger(ThreadState.class);

	public static void main(String[] args) {
		new Thread(new TimeWaiting(), "TimeWaitingThread").start();
		new Thread(new Waiting(), "WaitingThread").start();
		new Thread(new Blocked(), "BlockedThread").start();
		new Thread(new Blocked(), "BlockedThread").start();
	}

	/**
	 * 线程不断睡眠
	 */
	static class TimeWaiting implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 在Waiting.class实例上等待
	 */
	static class Waiting implements Runnable {
		@Override
		public void run() {
			while (true) {
				synchronized (Waiting.class) {
					try {
						Waiting.class.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 在Block.class实例上等待，不释放锁
	 */
	static class Blocked implements Runnable {
		@Override
		public void run() {
			synchronized (Blocked.class) {
				try {
					TimeUnit.SECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

package com.xicheng.concurrent.book.chapter05.code.code515;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * description
 *
 * @author xichengxml
 * @date 2019-08-26 18:07:20
 */
public class FairAndUnfairTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(FairAndUnfairTest.class);

	/**
	 * 公平锁
	 */
	private static ReentrantLock2 fairLock = new ReentrantLock2(true);

	/**
	 * 非公平锁
	 */
	private static ReentrantLock2 unfairLock = new ReentrantLock2(true);

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new Job(fairLock).start();
		}
		for (int i = 0; i < 5; i++) {
			new Job(unfairLock).start();
		}
	}

	private static class Job extends Thread {
		private ReentrantLock2 lock;

		public Job(ReentrantLock2 lock) {
			this.lock = lock;
		}

		@Override
		public void run() {
			LOGGER.info("current Thread：{}, waiting threads: {}", Thread.currentThread(), lock.getQueuedThreads());
		}
	}

	private static class ReentrantLock2 extends ReentrantLock {

		public ReentrantLock2(boolean fair) {
			super(fair);
		}

		@Override
		public Collection<Thread> getQueuedThreads() {
			List<Thread> threadList = new ArrayList<>(super.getQueuedThreads());
			Collections.reverse(threadList);
			return threadList;
		}
	}
}

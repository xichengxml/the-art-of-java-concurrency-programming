package com.xicheng.concurrent.book.chapter04.code.code413;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author liubin52
 * @date 2019-08-15 14:14:57
 */
public class Priority {

	public static final Logger LOGGER = LoggerFactory.getLogger(Priority.class);

	/**
	 * 启动flag
	 */
	private static volatile boolean start = false;

	/**
	 * 停止flag
	 */
	private static volatile boolean end = false;

	public static void main(String[] args) throws InterruptedException {
		List<Job> jobList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
			Job job = new Job(priority);
			jobList.add(job);
			Thread thread = new Thread(job, "Thread" + i);
			thread.setPriority(priority);
			thread.start();
		}

		// 运行10秒，查看所有线程的运行次数
		start = true;
		TimeUnit.SECONDS.sleep(10);
		end = true;

		for (Job job : jobList) {
			LOGGER.info("priority: {}, jobCount: {}", job.priority, job.jobCount);
		}
	}

	static class Job implements Runnable {

		/**
		 * 线程优先级
		 */
		private int priority;

		/**
		 * 线程调用计数
		 */
		private long jobCount;

		public Job(int priority) {
			this.priority = priority;
		}

		@Override
		public void run() {
			while (!start) {
				Thread.yield();
			}
			while (!end) {
				Thread.yield();
				jobCount++;
			}
		}
	}
}

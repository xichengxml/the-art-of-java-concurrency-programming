package com.xicheng.concurrent.mashibing.code001;

/**
 * description
 *
 * @author xichengxml
 * @date 2019-08-30 17:13:02
 */
public class SynchronizedExample03 {

	private int cnt = 1000;

	private static final int THREAD_CNT = 1000;

	/**
	 * 等同于synchronized(this)
	 */
	private synchronized void decrease() {
		cnt--;
		System.out.println("Thread name: " + Thread.currentThread().getName() + "--count: " + cnt);
	}

	public static void main(String[] args) {
		SynchronizedExample03 syncronizedExample = new SynchronizedExample03();
		for (int i = 0; i < THREAD_CNT; i++) {
			new Thread(() -> syncronizedExample.decrease()).start();
		}
	}
}

package com.xicheng.concurrent.mashibing;

import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author liubin52
 * @date 2019-08-30 17:22:30
 */
public class C04_Synchronized {

	private static int cnt = 1000;

	private static final int THREAD_CNT = cnt;

	/**
	 * 等同
	 */
	private static synchronized void decrease01() {
		cnt--;
		System.out.println("Thread name: " + Thread.currentThread().getName() + "--cnt: " + cnt);
	}

	private void decrease02() throws Exception {
		synchronized (C04_Synchronized.class) {
			cnt--;
			System.out.println("2--Thread name: " + Thread.currentThread().getName() + "--cnt: " + cnt);
			if (cnt == 0) {
				// 延时保证最后输出
				TimeUnit.SECONDS.sleep(5);
				System.out.println("线程安全");
			}
		}
	}

	/**
	 * 对比
	 */
	private void decrease03() throws Exception {
		synchronized (this) {
			cnt--;
			System.out.println("3--Thread name: " + Thread.currentThread().getName() + "--cnt: " + cnt);
			if (cnt == 0) {
				TimeUnit.SECONDS.sleep(5);
				System.out.println("线程安全");
			}
		}
	}

	/**
	 * 这种情况下decrease02能够保证原子性，decrease03不能
	 *
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < THREAD_CNT; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						// new SynchronizedExample04().decrease02();
						new C04_Synchronized().decrease03();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}

		// 等待所有线程完成
		Thread.currentThread().join();
	}

}
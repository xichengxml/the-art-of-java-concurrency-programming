package com.xicheng.concurrent.mashibing.code001;

/**
 * description
 *
 * @author liubin52
 * @date 2019-08-30 17:13:02
 */
public class SynchronizedExample02 {

	private int cnt = 10;

	/**
	 *
	 */
	private void decrease() {
		// 任何线程想要执行以下代码，必须先拿到this的锁
		synchronized (this) {
			cnt--;
			System.out.println("Thread name: " + Thread.currentThread().getName() + "--count: " + cnt);
		}
	}

	public static void main(String[] args) {
		SynchronizedExample02 syncronizedExample = new SynchronizedExample02();
		for (int i = 0; i < 10; i++) {
			syncronizedExample.decrease();
		}
	}
}

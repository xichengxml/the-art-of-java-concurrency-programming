package com.xicheng.concurrent.mashibing.code001;

/**
 * synchronized关键字
 * 对某个对象加锁
 *
 * @author liubin52
 * @date 2019-08-30 17:06:05
 */
public class SynchronizedExample01 {

	private int cnt = 10;

	private final Object object = new Object();

	public static void main(String[] args) {
		SynchronizedExample01 synchronizedExample = new SynchronizedExample01();
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					synchronizedExample.decrease();
				}
			}).start();
		}
	}

	private void decrease() {
		// 任何线程想要执行以下代码，必须拿到object的锁
		synchronized (object) {
			cnt--;
			System.out.println("Thread name: " + Thread.currentThread().getName() + " count: " + cnt);
		}
	}
}

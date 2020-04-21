package com.xicheng.concurrent.mashibing.code003;

/**
 * description
 *
 * @author xichengxml
 * @date 2019-08-30 18:07:08
 */
public class SynchronizedThread02 implements Runnable {

	private int cnt = 10;

	@Override
	public synchronized void run() {
		cnt--;
		System.out.println("Thread name: " + Thread.currentThread().getName() + "--cnt: " + cnt);
	}

	public static void main(String[] args) {
		SynchronizedThread02 synchronizedThread = new SynchronizedThread02();
		for (int i = 0; i < 10; i++) {
			new Thread(synchronizedThread).start();
		}
	}
}

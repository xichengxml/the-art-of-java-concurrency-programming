package com.xicheng.concurrent.mashibing;

/**
 * description
 *
 * @author liubin52
 * @date 2019-08-30 18:07:08
 */
public class C06_Synchronized implements Runnable {

	private int cnt = 10;

	@Override
	public synchronized void run() {
		cnt--;
		System.out.println("Thread name: " + Thread.currentThread().getName() + "--cnt: " + cnt);
	}

	public static void main(String[] args) {
		C06_Synchronized synchronizedThread = new C06_Synchronized();
		for (int i = 0; i < 10; i++) {
			new Thread(synchronizedThread).start();
		}
	}
}

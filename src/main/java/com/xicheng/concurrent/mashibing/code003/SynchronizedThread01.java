package com.xicheng.concurrent.mashibing.code003;

/**
 * 分析一下输出
 *
 * @author xichengxml
 * @date 2019-08-30 18:02:08
 */
public class SynchronizedThread01 implements Runnable {

	private int cnt = 10;

	@Override
	public void run() {
		cnt--;
		System.out.println("Thread name: " + Thread.currentThread().getName() + "--cnt: " + cnt);
	}

	public static void main(String[] args) {
		SynchronizedThread01 synchronizedThread = new SynchronizedThread01();
		for (int i = 0; i < 10; i++) {
			new Thread(synchronizedThread).start();
		}
	}
}

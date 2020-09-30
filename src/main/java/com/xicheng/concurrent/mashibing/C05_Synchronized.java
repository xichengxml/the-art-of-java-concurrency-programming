package com.xicheng.concurrent.mashibing;

/**
 * 分析一下输出
 *
 * @author xichengxml
 * @date 2019-08-30 18:02:08
 */
public class C05_Synchronized implements Runnable {

	private int cnt = 10;

	@Override
	public void run() {
		cnt--;
		System.out.println("Thread name: " + Thread.currentThread().getName() + "--cnt: " + cnt);
	}

	public static void main(String[] args) {
		C05_Synchronized synchronizedThread = new C05_Synchronized();
		for (int i = 0; i < 10; i++) {
			new Thread(synchronizedThread).start();
		}
	}
}

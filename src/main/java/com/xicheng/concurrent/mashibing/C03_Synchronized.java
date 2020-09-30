package com.xicheng.concurrent.mashibing;

/**
 * description
 *
 * @author liubin52
 * @date 2019-08-30 17:13:02
 */
public class C03_Synchronized {

	private int cnt = 1000;

	private static final int ThreadCnt = 1000;

	/**
	 * 等同于synchronized(this)
	 */
	private synchronized void decrease() {
		cnt--;
		System.out.println("Thread name: " + Thread.currentThread().getName() + "--count: " + cnt);
	}

	public static void main(String[] args) {
		C03_Synchronized synchronizedExample = new C03_Synchronized();
		for (int i = 0; i < ThreadCnt; i++) {
			synchronizedExample.decrease();
		}
	}
}

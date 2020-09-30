package com.xicheng.concurrent.mashibing;

import java.util.concurrent.TimeUnit;

/**
 * 同步方法和非同步方法是否可以同时调用
 *
 * @author liubin52
 * @date 2019-08-30 18:12:15
 */
public class C07_Synchronized {

	private synchronized void print01() {
		System.out.println("print01 start...");
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("print01 end");
	}

	private void print02() {
		System.out.println("print02 start...");
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("print02 end");
	}

	public static void main(String[] args) {
		C07_Synchronized synchronizedExample = new C07_Synchronized();
		new Thread(() -> synchronizedExample.print01(), "t1").start();
		new Thread(() -> synchronizedExample.print02(), "t2").start();
	}
}

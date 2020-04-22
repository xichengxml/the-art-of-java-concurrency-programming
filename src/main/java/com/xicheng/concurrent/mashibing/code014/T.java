package com.xicheng.concurrent.mashibing.code014;

import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 不要使用字符串常量做为锁对象，因为相同的字符串常量在内存中是同一个对象
 * 这种情况下可能会发生诡异的死锁
 *
 * @author xichengxml
 * @date 2019-08-31 11:02
 */
@Slf4j
public class T {

	private final String s1 = "Hello";

	private final String s2 = "Hello";

	private void m1() {
		synchronized (s1) {
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.info(Thread.currentThread().getName());
		}
	}

	private void m2() {
		synchronized (s2) {
			log.info(Thread.currentThread().getName());
		}
	}

	public static void main(String[] args) throws Exception {
		T t1 = new T();
		T t2 = new T();
		log.info("start....");
		// 看起来是不同的对象，实际上使用的是同一把锁
		ThreadPoolUtil.executeThread(t1::m1);
		// 睡眠一下，保证t1先获得锁
		TimeUnit.SECONDS.sleep(2);

		ThreadPoolUtil.executeThread(t2::m2);

		// 等待其他线程完成
		Thread.currentThread().join();
	}
}

package com.xicheng.concurrent.mashibing.code001;

import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * synchronized关键字
 * 对某个对象加锁
 *
 * @author xichengxml
 * @date 2019-08-30 17:06:05
 */
@Slf4j
public class SynchronizedExample01 {

	private int cnt = 10;

	private final Object object = new Object();

	public static void main(String[] args) {
		SynchronizedExample01 synchronizedExample = new SynchronizedExample01();
		for (int i = 0; i < 10; i++) {
			ThreadPoolUtil.executeThread(synchronizedExample::decrease);
		}
	}

	private void decrease() {
		// 任何线程想要执行以下代码，必须拿到object的锁
		synchronized (object) {
			cnt--;
			log.info("Thread name: " + Thread.currentThread().getName() + " count: " + cnt);
		}
	}
}

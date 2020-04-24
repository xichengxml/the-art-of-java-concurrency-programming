package com.xicheng.concurrent.mashibing.code016;

import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可以使用lockInterruptibly响应中断
 *
 * @author xichengxml
 * @date 2019-09-07 16:39
 */
@Slf4j
public class ReentrantLock04 {

	private ReentrantLock lock = new ReentrantLock();

	private void m1() {
		lock.lock();
		try {
			log.info("m1 start");
			TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
			log.info("m1 end");
		} catch (InterruptedException e) {
			log.info("m1 interrupt");
		} finally {
			lock.unlock();
		}
	}

	private void m2() {
		try {
			lock.lockInterruptibly();
			log.info("m2 start");
			TimeUnit.SECONDS.sleep(5);
			log.info("m2 end");
		} catch (InterruptedException e) {
			log.info("m2 interrupt");
		} finally {
			if (lock.isLocked()) {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		ReentrantLock04 instance = new ReentrantLock04();
		ThreadPoolUtil.executeThread(instance::m1);
		TimeUnit.SECONDS.sleep(1);
		Thread t2 = new Thread(instance::m2, "t2");
		t2.start();
		TimeUnit.SECONDS.sleep(1);

		t2.interrupt();
	}
}

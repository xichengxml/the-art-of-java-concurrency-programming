package com.xicheng.concurrent.mashibing.code016;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock还可指定为公平锁
 *
 * @author xichengxml
 * @date 2019-09-07 17:22
 */
@Slf4j
public class ReentrantLock05 extends Thread {

	/**
	  * 注意这里是static，锁才具有唯一性，否则每次new对象都会产生一把锁
	 */
    private static ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            lock.lock();
            try {
	            log.info("name: {}, 获得了锁: {}", Thread.currentThread().getName(), i);
	            TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
	            lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLock05 t1 = new ReentrantLock05();
        t1.setName("t1");
        ReentrantLock05 t2 = new ReentrantLock05();
        t2.setName("t2");
        ReentrantLock05 t3 = new ReentrantLock05();
        t3.setName("t3");
        ReentrantLock05 t4 = new ReentrantLock05();
        t4.setName("t4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

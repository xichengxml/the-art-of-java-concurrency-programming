package com.xicheng.concurrent.mashibing;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock还可指定为公平锁
 *
 * @author xichengxml
 * @date 2019-09-07 17:22
 */
public class C26_ReentrantLock extends Thread {

    // 注意这里是static，锁才具有唯一性，否则每次new对象都会产生一把锁
    private static ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "获得了锁" + i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        C26_ReentrantLock t1 = new C26_ReentrantLock();
        t1.setName("t1");
        C26_ReentrantLock t2 = new C26_ReentrantLock();
        t2.setName("t2");
        C26_ReentrantLock t3 = new C26_ReentrantLock();
        t3.setName("t3");
        C26_ReentrantLock t4 = new C26_ReentrantLock();
        t4.setName("t4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

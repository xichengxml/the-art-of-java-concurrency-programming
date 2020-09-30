package com.xicheng.concurrent.mashibing;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可以使用lockInterruptibly响应中断
 *
 * @author xichengxml
 * @date 2019-09-07 16:39
 */
public class C25_ReentrantLock {

    private ReentrantLock lock = new ReentrantLock();

    private void m1() {
        lock.lock();
        try {
            System.out.println("m1 start");
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
            System.out.println("m1 end");
        } catch (InterruptedException e) {
            System.out.println("m1 interrupt");
        } finally {
            lock.unlock();
        }
    }

    private void m2() {
        try {
            lock.lockInterruptibly();
            System.out.println("m2 start");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("m2 end");
        } catch (InterruptedException e) {
            System.out.println("m2 interrupt");
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        C25_ReentrantLock instance = new C25_ReentrantLock();
        new Thread(instance::m1, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(instance::m2, "t2");
        t2.start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();
    }
}

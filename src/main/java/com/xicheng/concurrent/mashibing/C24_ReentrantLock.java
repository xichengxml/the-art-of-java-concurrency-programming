package com.xicheng.concurrent.mashibing;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock时可以使用tryLock尝试获取锁
 *
 * @author xichengxml
 * @date 2019-09-07 16:23
 */
public class C24_ReentrantLock {

    private ReentrantLock lock = new ReentrantLock();

    private void m1() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("m1: " + i);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void m2() {
        boolean locked = false;
        try {
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("m2");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        C24_ReentrantLock instance = new C24_ReentrantLock();
        new Thread(instance::m1, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(instance::m2, "t2").start();
    }
}

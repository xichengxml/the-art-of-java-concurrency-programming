package com.xicheng.concurrent.mashibing.code016;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock实现
 * <p>
 * 注意：synchronized出现异常时jvm会自动释放锁，但是ReentrantLock需要手动释放
 *
 * @author xichengxml
 * @date 2019-09-07 16:15
 */
public class ReentrantLock02 {

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
        lock.lock();
        System.out.println("m2");
        lock.unlock();
    }

    public static void main(String[] args) {
        ReentrantLock02 instance = new ReentrantLock02();
        new Thread(instance::m1, "t1").start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(instance::m2, "t2").start();
    }
}


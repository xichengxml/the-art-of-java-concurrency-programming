package com.xicheng.concurrent.mashibing;

import java.util.concurrent.TimeUnit;

/**
 * 回顾synchronized用法
 *
 * @author xichengxml
 * @date 2019-09-07 16:10
 */
public class C22_ReentrantLock {

    private synchronized void m1() {
        for (int i = 0; i < 10; i++) {
            System.out.println("m1: " + i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void m2() {
        System.out.println("m2");
    }

    public static void main(String[] args) {
        C22_ReentrantLock instance = new C22_ReentrantLock();
        new Thread(instance::m1, "t1").start();

        // 保证t1先被执行
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(instance::m2, "t2").start();
    }
}

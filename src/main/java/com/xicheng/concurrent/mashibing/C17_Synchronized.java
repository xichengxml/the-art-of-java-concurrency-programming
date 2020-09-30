package com.xicheng.concurrent.mashibing;

import java.util.concurrent.TimeUnit;

/**
 * 不要使用字符串常量做为锁对象，因为相同的字符串常量在内存中是同一个对象
 * 这种情况下可能会发生诡异的死锁
 *
 * @author xichengxml
 * @date 2019-08-31 11:02
 */
public class C17_Synchronized {

    private final String s1 = "Hello";

    private final String s2 = "Hello";

    private void m1() {
        synchronized (s1) {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }
    }

    private void m2() {
        synchronized (s2) {
            System.out.println(Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        C17_Synchronized t1 = new C17_Synchronized();
        C17_Synchronized t2 = new C17_Synchronized();

        // 看起来是不同的对象，实际上使用的是同一把锁
        new Thread(t1::m1, "t1").start();

        // 睡眠一下，保证t1先获得锁
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(t2::m2, "t2").start();

        // 等待其他线程完成
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

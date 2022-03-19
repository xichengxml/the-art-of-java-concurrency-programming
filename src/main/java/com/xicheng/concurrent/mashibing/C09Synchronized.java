package com.xicheng.concurrent.mashibing;

import java.util.concurrent.TimeUnit;

/**
 * 一个同步方法可以调用另一个同步方法，一个对象持有了某个对象的锁，再次申请时仍然可以获得这个对象的锁
 * 也就是说，synchronized是可重入的
 *
 * @author xichengxml
 * @date 2019-08-31 05:14
 */
public class C09Synchronized {

    private synchronized void m1() {
        System.out.println("m1 start...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m1 end");
    }

    private synchronized void m2() {
        System.out.println("m2 start...");
    }

    public static void main(String[] args) {
        C09Synchronized t = new C09Synchronized();
        t.m1();
    }
}

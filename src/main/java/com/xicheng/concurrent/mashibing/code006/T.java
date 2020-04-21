package com.xicheng.concurrent.mashibing.code006;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 一个同步方法可以调用另一个同步方法，一个线程持有了某个对象的锁，再次申请时仍然可以获得这个对象的锁
 * 也就是说，synchronized是可重入的
 *
 * @author xichengxml
 * @date 2019-08-31 05:14
 */
@Slf4j
public class T {

    private synchronized void m1() {
        log.info("m1 start...");
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
        log.info("m1 end");
    }

    private synchronized void m2() {
        log.info("m2 start...");
    }

    public static void main(String[] args) {
        T t = new T();
        t.m1();
    }
}

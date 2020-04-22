package com.xicheng.concurrent.mashibing.code012;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * synchronized优化，降低锁粒度
 * synchronized中的代码越少越好
 *
 * @author xichengxml
 * @date 2019-08-31 10:08
 */
@Slf4j
public class T {

    /**
     * 仅用于m1执行次数计数
     */
    private int cnt1 = 0;

    /**
     * 仅用于m2执行次数计数
     */
    private int cnt2 = 0;

    private void m1() {
        synchronized (this) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // log.info(Thread.currentThread().getName() + " running...");
            cnt1++;
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void m2() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            // log.info(Thread.currentThread().getName() + "running...");
            cnt2++;
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 需要分别统计，所以分别用两个对象做为锁
        T t1 = new T();
        T t2 = new T();
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            threadList.add(new Thread(() -> {
                while (true) {
                    t1.m1();
                }
            } , "thread-m1-" + i));
            threadList.add(new Thread(() -> {
                while (true) {
                    t2.m2();
                }
            }, "thread-m2-" + i));
        }

        threadList.forEach(o -> o.start());

        // 模拟执行一段时间，比较二者执行效率
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("{}", t1.cnt1);
        log.info("{}", t2.cnt2);

        threadList.forEach(o -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        log.info("finished");
    }

}

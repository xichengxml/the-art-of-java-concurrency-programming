package com.xicheng.concurrent.mashibing;

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
public class C15_Synchronized {

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
            System.out.println(Thread.currentThread().getName() + " running...");
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
            System.out.println(Thread.currentThread().getName() + "running...");
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
        C15_Synchronized t1 = new C15_Synchronized();
        C15_Synchronized t2 = new C15_Synchronized();
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

        threadList.forEach(Thread::start);

        // 模拟执行一段时间，比较二者执行效率
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t1.cnt1);
        System.out.println(t2.cnt2);

        threadList.forEach(o -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("finished");
    }

}

package com.xicheng.concurrent.mashibing.code013;

import java.util.concurrent.TimeUnit;

/**
 * 锁对象o其中的属性发生变化不影响锁的使用，但是对象引用发生变化会影响使用，
 * 也可以间接证明锁的对象是堆内存里的对象，而不是栈内存中的引用
 * 因此，要避免在同步的过程中改变对象，锁对象最好用final关键字修饰
 *
 * @author xichengxml
 * @date 2019-08-31 10:39
 */
public class T {

    private int cnt = 0;

    private synchronized void m() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        T t = new T();
        new Thread(t::m, "t1").start();

        // 改变对象中的某个属性
        System.out.println("change field");
        t.cnt++;
        new Thread(t::m, "t2").start();

        // 改变对象引用
        System.out.println("change reference");
        t = new T();
        new Thread(t::m, "t3").start();
    }
}

package com.xicheng.concurrent.mashibing;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 该程序是要实现多个线程等待的情况
 *
 * @author xichengxml
 * @date 2019-09-26 01:02
 */
public class C39_LinkedBlockingQueue {

    private static BlockingDeque<String> blockingDeque = new LinkedBlockingDeque<>(2);

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    // 如果空了，就会等待
                    System.out.println(Thread.currentThread().getName() + " take " + blockingDeque.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "t" + i).start();
        }

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    String value = "value" + atomicInteger.getAndIncrement();
                    blockingDeque.put(value);
                    System.out.println(value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

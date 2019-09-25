package com.xicheng.concurrent.mashibing.code022;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 该程序的目的是实现多个线程往队列里加，然后满了就会等待，等到下面的线程取的时候，再继续放入
 *
 * @author xichengxml
 * @date 2019-09-26 00:44
 */
public class T01_LinkedBlockingQueue {

    private static BlockingDeque<String> blockingDeque = new LinkedBlockingDeque<>(2);

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    String value = "value" + atomicInteger.getAndIncrement();
                    // 如果满了，就会等待
                    blockingDeque.put(value);
                    System.out.println(Thread.currentThread().getName() + " put " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "t" + i).start();
        }

        new Thread(() -> {
            for (; ; ) {
                System.out.println(blockingDeque.removeFirst());
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}

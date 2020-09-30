package com.xicheng.concurrent.mashibing;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 写时复制容器；使用空间换时间
 * 多线程环境下，写效率低，读效率高
 * 多用于读多写少的环境
 *
 * 速度较慢，耐心等待
 *
 * 性能排序：SynchronizedList > Vector > ConcurrentLinkedQueue > copyOnWriteArrayList
 *
 * @author xichengxml
 * @date 2019-09-22 14:32
 */
public class C37_CopyOnWriteList {

    private static AtomicLong cnt = new AtomicLong(0L);

    public static void main(String[] args) throws Exception {
        List<Long> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        CountDownLatch countDownLatch01 = new CountDownLatch(100);
        long start01 = System.nanoTime();
        batchAdd(countDownLatch01, copyOnWriteArrayList);
        countDownLatch01.await();
        System.out.println("copyOnWriteArrayList: " + copyOnWriteArrayList.size());
        System.out.println(System.nanoTime() - start01);

        List<Long> vector = new Vector<>();
        CountDownLatch countDownLatch02 = new CountDownLatch(100);
        long start02 = System.nanoTime();
        batchAdd(countDownLatch02, vector);
        countDownLatch02.await();
        System.out.println("Vector: " + vector.size());
        System.out.println(System.nanoTime() - start02);

        List<Long> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch03 = new CountDownLatch(100);
        long start03 = System.nanoTime();
        batchAdd(countDownLatch03, synchronizedList);
        countDownLatch03.await();
        System.out.println("SynchronizedList: " + synchronizedList.size());
        System.out.println(System.nanoTime() - start03);

        Queue<Long> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        CountDownLatch countDownLatch04 = new CountDownLatch(100);
        long start04 = System.nanoTime();
        batchAdd(countDownLatch04, concurrentLinkedQueue);
        countDownLatch04.await();
        System.out.println("ConcurrentLinkedQueue: " + concurrentLinkedQueue.size());
        System.out.println(System.nanoTime() - start04);
    }

    private static void batchAdd(CountDownLatch countDownLatch, Collection<Long> collection) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    collection.add(cnt.getAndIncrement());
                }
                countDownLatch.countDown();
            }).start();
        }
    }
}

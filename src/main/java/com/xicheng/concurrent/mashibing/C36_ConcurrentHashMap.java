package com.xicheng.concurrent.mashibing;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 对比多种原子性map的性能
 * 注：最后一种有点问题，待解决，size不对
 * 要保证key生成时具有原子性，不然在添加时会导致覆盖，size小于100万
 *
 * 性能排序：ConcurrentSkipListMap > HashTable > ConcurrentHashMap > SynchronizedMap
 *
 * @author xichengxml
 * @date 2019-09-22 14:06
 */
public class C36_ConcurrentHashMap {

    private static AtomicLong cnt = new AtomicLong(0L);

    public static void main(String[] args) throws Exception {

        Map<Long, String> concurrentHashMap = new ConcurrentHashMap<>();
        CountDownLatch countDownLatch01 = new CountDownLatch(100);
        long start01 = System.nanoTime();
        batchAdd(countDownLatch01, concurrentHashMap);
        countDownLatch01.await();
        long runTime01 = System.nanoTime() - start01;
        System.out.println("ConcurrentHashMap: " + concurrentHashMap.size());
        System.out.println(runTime01);

        Map<Long, String> hashTable = new Hashtable<>();
        CountDownLatch countDownLatch02 = new CountDownLatch(100);
        long start02 = System.nanoTime();
        batchAdd(countDownLatch02, hashTable);
        countDownLatch02.await();
        long runTime02 = System.nanoTime() - start02;
        System.out.println("HashTable: " + hashTable.size());
        System.out.println(runTime02);

        Map<Long, String> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        CountDownLatch countDownLatch03 = new CountDownLatch(100);
        long start03 = System.nanoTime();
        batchAdd(countDownLatch03, synchronizedMap);
        countDownLatch03.await();
        long runTime03 = System.nanoTime() - start03;
        System.out.println("SynchronizedMap: " + synchronizedMap.size());
        System.out.println(runTime03);

        Map<Long, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();
        CountDownLatch countDownLatch04 = new CountDownLatch(100);
        long start04 = System.nanoTime();
        batchAdd(countDownLatch04, concurrentSkipListMap);
        long runTime04 = System.nanoTime() - start04;
        System.out.println("ConcurrentSkipListMap: " + concurrentSkipListMap.size());
        System.out.println(runTime04);
    }

    private static void batchAdd(CountDownLatch countDownLatch, Map<Long, String> map) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    map.put(cnt.getAndIncrement(), "value");
                }
                countDownLatch.countDown();
            }).start();

        }
    }
}

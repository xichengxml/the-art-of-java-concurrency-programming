package com.xicheng.concurrent.mashibing;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用lock和condition实现，对比上例，condition更能精确的控制唤醒哪些线程
 *
 * put和get加上synchronized会死锁，想想为什么
 *
 * @author xichengxml
 * @date 2019-09-21 03:27
 */
public class C28_MyContainer<T> {

    private final LinkedList<T> container = new LinkedList<>();

    private static final int MAX = 10;

    private static final int ZERO = 0;

    private Lock lock = new ReentrantLock();
    private Condition producer = lock.newCondition();
    private Condition consumer = lock.newCondition();

    private void put(T t) {
        // 想想为什么用while不用if
        try {
            lock.lock();
            while (container.size() == MAX) {
                producer.await();
            }
            container.add(t);
            consumer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private T get() {
        T t = null;
        try {
            lock.lock();
            while (container.size() == ZERO) {
                consumer.await();
            }
            t = container.removeFirst();
            producer.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return t;
    }

    public static void main(String[] args) {
        C28_MyContainer<String> container02 = new C28_MyContainer<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    String s = container02.get();
                   /* try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    System.out.println(s);
                }
            }, "consumer-" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    container02.put(Thread.currentThread().getName() + "-" + j);
                }
            }, "producer-" + i).start();
        }
    }
}

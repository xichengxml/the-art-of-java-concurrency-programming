package com.xicheng.concurrent.tuling;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class C04_Condition {

    private static final List<String> list = new ArrayList<>();

    private static final int MAX = 10;

    private static final Lock lock = new ReentrantLock();
    private static final Condition producer = lock.newCondition();
    private static final Condition consumer = lock.newCondition();

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            new Thread(C04_Condition::put, "t-" + i).start();
        }

        new Thread(C04_Condition::get).start();

        LockSupport.park();
    }

    private static void put() {
        while (true) {
            lock.lock();
            try {
                while (list.size() == MAX) {
                    try {
                        producer.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                list.add(Thread.currentThread().getName());
                consumer.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    private static void get() {
        while (true) {
            lock.lock();
            try {
                while (list.isEmpty()) {
                    try {
                        consumer.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(list.remove(0));
                producer.signalAll();
            } finally {
                lock.unlock();
            }
        }

    }
}

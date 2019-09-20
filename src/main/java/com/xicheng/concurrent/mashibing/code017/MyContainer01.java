package com.xicheng.concurrent.mashibing.code017;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 写一个固定容量同步容器，拥有put和get方法， 以及getCount方法，
 * 能够支持2个生产者线程和10个消费者线程的调用
 *
 * 使用wait和notify实现
 *
 * @author xichengxml
 * @date 2019-09-21 03:06
 */
public class MyContainer01<T> {

    private final LinkedList<T> container = new LinkedList<>();

    private static final int MAX = 10;

    private static final int ZERO = 0;

    private synchronized void put(T t) {
        // 想想为什么用while而不用if
        while (container.size() == MAX) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        container.add(t);
        // 通知消费者进行消费
        this.notifyAll();
    }

    private synchronized T get() {
        while (container.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        T t = container.removeFirst();
        // 通知生产者生产
        this.notifyAll();
        return t;
    }

    public static void main(String[] args) {
        MyContainer01<String> container01 = new MyContainer01<>();
        // 启动消费者线程
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    String s = container01.get();
                    System.out.println(s);
                }
            }, "consumer-" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 启动生产者线程
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    container01.put(Thread.currentThread().getName() + "-" + j);
                }
            }, "producer-" + i).start();
        }
    }
}

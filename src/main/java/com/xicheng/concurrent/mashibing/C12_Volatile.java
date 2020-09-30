package com.xicheng.concurrent.mashibing;

import java.util.concurrent.TimeUnit;

/**
 * volatile关键字，使一个变量在多个线程间可见
 * A B线程都用到一个变量，java默认是A线程中保留一份copy，这样如果B线程修改了该变量，则A线程未必知道
 * 使用volatile关键字，会让所有线程都会读到变量的修改值
 *
 * 在下面的代码中，running是存在与堆内存的t对象中
 * 当线程t1开始运行的时候，会把running值从内存读到读到t1线程的工作区，在运行过程中直接
 * 使用这个copy，并不会每次都去读取堆内存，这样，当主线程修改running的值之后，t1线程
 * 感知不到，所以不会停止运行
 *
 * 使用volatile，将会强制所有线程都去堆内存中读取running的值
 *
 * volatile并不能保证多个线程功能修改running变量时所带来的的不一致问题，也就是说volatile
 * 不能替代synchronized
 *
 * @author xichengxml
 * @date 2019-08-31 08:02
 */
public class C12_Volatile {

    // 对比有无volatile的情况下，整个程序运行结果的区别
    private /*volatile*/ boolean running = true;

    // 纯粹用于打印使用，当计时器使
    private int cnt = 0;

    private void m() {
        System.out.println("m start...");
        while (running) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ", cnt: " + cnt++);
        }
        System.out.println("m end...");
    }

    public static void main(String[] args) {
        C12_Volatile t = new C12_Volatile();
        new Thread(() -> t.m(), "t1").start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.running = false;
    }
}

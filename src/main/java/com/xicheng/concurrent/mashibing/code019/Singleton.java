package com.xicheng.concurrent.mashibing.code019;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程安全的单例模式
 * 既不用加锁，也能实现懒加载
 * http://www.cnblogs.com/xudong-bupt/p/3433643.html
 *
 * @author xichengxml
 * @date 2019-09-21 21:53
 *
 */
public class Singleton {

    private Singleton() {
        System.out.println("singleton");
    }

    private static class InnerClass {
        private static Singleton singleton = new Singleton();
    }

    public static Singleton getInstance() {
        return InnerClass.singleton;
    }

    public static void main(String[] args) {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            threadList.add(new Thread(() -> {
                Singleton instance = Singleton.getInstance();
                System.out.println(Thread.currentThread().getName() + "-" + instance);
            }, getName(i)));
        }
        threadList.forEach(o -> o.start());
    }

    private static String getName(int i) {
        String name = null;
        if (i < 10) {
            name = "t00" + i;
        } else if (i < 100) {
            name = "t0" + i;
        } else {
            name = "t" + i;
        }
        return name;
    }
}

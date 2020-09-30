package com.xicheng.concurrent.mashibing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 实现一个容器，提供两个方法，add和size
 * 写两个线程，线程1往容器中添加10个元素，线程2监控元素的个数，当元素个数达到5个时，线程2给出预警
 *
 * 实现思路1：给lists添加volatile关键字，使线程2可以及时得到通知，但是死循环比较耗费资源
 *
 * @author xichengxml
 * @date 2019-09-07 14:44
 */
public class C18_MyContainer {

    private volatile List<Integer> list = new ArrayList();

    private void add(int element) {
        list.add(element);
    }

    private int size() {
        return list.size();
    }

    public static void main(String[] args) {

        C18_MyContainer container = new C18_MyContainer();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " add element: " + i);
                container.add(i);

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                if (container.size() == 5) {
                    System.out.println("send some notification...");
                }
            }
        }).start();


    }
}

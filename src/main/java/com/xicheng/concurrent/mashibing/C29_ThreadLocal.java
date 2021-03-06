package com.xicheng.concurrent.mashibing;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal局部变量
 *
 * @author xichengxml
 * @date 2019-09-21 16:56
 */
public class C29_ThreadLocal {

    volatile static Person person = new Person();

    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(person.name);
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            person.name = "lisi";
        }).start();
    }

    static class Person {
        String name = "zhangsan";
    }
}

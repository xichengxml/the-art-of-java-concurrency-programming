package com.xicheng.concurrent.mashibing;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal线程局部变量
 *
 * ThreadLocal是使用空间换时间，synchronized是使用时间换空间
 * 比如在hibernate中session就存在ThreadLocal中，避免synchronized的使用
 *
 * @author xichengxml
 * @date 2019-09-21 17:00
 */
public class C30_Threadlocal {

    private static ThreadLocal<Person> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(() -> {

            threadLocal.set(new Person("t1"));
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadLocal.get());
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadLocal.set(new Person("t2"));
        }).start();

    }

    static class Person {
        String name;

        private Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}


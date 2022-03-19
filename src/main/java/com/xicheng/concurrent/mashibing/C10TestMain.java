package com.xicheng.concurrent.mashibing;

/**
 * 一个同步方法可以调用另一个同步方法，线程获取了一个对象的锁，再次获取时仍然可以获得该对象的锁
 * 也就是说，synchronized是可重入的
 * 子类同步方法调用父类同步方法也一样
 *
 * 想一想这个锁定的对象是谁
 * @author xichengxml
 * @date 2019-08-31 06:04
 */
public class C10TestMain {

    public static void main(String[] args) {
        C10Son son = new C10Son();
        son.m();
    }
}

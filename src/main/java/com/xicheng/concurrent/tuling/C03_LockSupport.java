package com.xicheng.concurrent.tuling;

import java.util.concurrent.locks.LockSupport;

/**
 * @description LockSupport源码
 * @author xichengxml
 * @date 2021-03-07 10:30:38
 */
public class C03_LockSupport {

    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        System.out.println(mainThread);
        LockSupport.park();
    }
}

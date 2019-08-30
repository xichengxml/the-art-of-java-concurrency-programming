package com.xicheng.concurrent.book.chapter01.code120;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description
 *
 * @author xichengxml
 * @date 2019-08-10 23:27
 */
public class DeadLockDemo {

    private static final String A = "A";

    private static final String B = "B";

    private static final Logger LOGGER = LoggerFactory.getLogger(DeadLockDemo.class);

    public static void main(String[] args) {
        deadLock();
    }

    private static void deadLock() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (A) {
                    try {
                        // 这里等两秒的目的是让线程t2启动并锁定B，且sleep不释放锁，所以会进入死锁状态
                        Thread.sleep(2000);
                        // 改成wait之后，本意是想测试wait是释放锁的，但抛出了java.lang.IllegalMonitorStateException
                        // 的异常，原因未明，待完成
                        // Thread.currentThread().wait(2000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (B) {
                        LOGGER.info("1");
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (B) {
                    synchronized (A) {
                        LOGGER.info("2");
                    }
                }
            }
        });

        t1.start();
        t2.start();
    }
}

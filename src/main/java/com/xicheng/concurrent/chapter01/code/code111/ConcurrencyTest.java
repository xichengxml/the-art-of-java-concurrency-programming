package com.xicheng.concurrent.chapter01.code.code111;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author xichengxml
 * @date 2019-08-10 22:19
 */
public class ConcurrencyTest {

    /**
     * 这个地方用int就可以，没必要用long
     */
    private static final int COUNT = 100000000;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrencyTest.class);

    public static void main(String[] args) throws Exception {
        concurrency();
        serial();
    }

    /**
     * 运行了两个线程，单独开一个线程用来做加法操作，主线程用来做减法操作，然后join，等待分线程完成，一起返回结果，打印日志
     * @throws Exception
     */
    private static void concurrency() throws Exception {
        long start = System.nanoTime();
        int a = 0;
        ConcurrencyThread concurrencyThread = new ConcurrencyThread(a);
        Thread thread = new Thread(concurrencyThread);
        thread.start();
        int b = 0;
        for (int i = 0; i < COUNT; i++) {
            b -= 5;
        }
        thread.join();
        long runTime = System.nanoTime() - start;
        LOGGER.info("concurrency runTime: {}, a = {}, b = {}", runTime, concurrencyThread.getA(), b);
    }

    /**
     * 直接用主线程做加减运算
     */
    private static void serial() {
        long start = System.nanoTime();
        int a = 0;
        for (int i = 0; i < COUNT; i++) {
            a += 5;
        }
        int b = 0;
        for (int i = 0; i < COUNT; i++) {
            b -= 5;
        }
        long runTime = System.nanoTime() - start;
        LOGGER.info("serial runTime:{}. a = {}, b = {}", runTime, a, b);
    }

    static class ConcurrencyThread implements Runnable {

        private int a;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

        private ConcurrencyThread(int a) {
            this.a = a;
        }

        @Override
        public void run() {
            for (int i = 0; i < COUNT; i++) {
                a += 5;
            }
        }
    }

}

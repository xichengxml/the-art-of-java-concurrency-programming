package com.xicheng.concurrent.mashibing.code026;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的概念
 *
 * @author xichengxml
 * @date 2019-10-03 16:27
 */
@Slf4j
public class T05_ThreadPool {

    private static final ExecutorService threadPool = Executors.newFixedThreadPool(5);

    public static void main(String[] args){
        for (int i = 0; i < 6; i++) {
            threadPool.execute(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                }
                log.info(Thread.currentThread().getName());
            });
        }

        System.out.println(threadPool);

        // 对比一下shutdown和shutdownNow的区别
        threadPool.shutdown();
        /*List<Runnable> runnableList = null;
        try {
            runnableList = threadPool.shutdownNow();
        } catch (Exception e) {
        }
        for (Runnable runnable : runnableList) {
            System.out.println(runnable);
        }*/
        System.out.println(threadPool.isTerminated());
        System.out.println(threadPool.isShutdown());
        System.out.println(threadPool);

        // 等待所有线程执行完成
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(threadPool.isTerminated());
        System.out.println(threadPool.isShutdown());
        System.out.println(threadPool);
    }
}

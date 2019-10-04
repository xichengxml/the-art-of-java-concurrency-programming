package com.xicheng.concurrent.mashibing.code026;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * 认识{@link java.util.concurrent.Executor}
 *
 * @author xichengxml
 * @date 2019-10-03 09:46
 */
@Slf4j
public class T01_MyExecutor {

    public static void main(String[] args) {
        log.info("start");
        Executor01 executor01 = new Executor01();
        executor01.execute(() -> {
            // 同步，使用的是主线程
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("This is executor01 thread running");
        });
        log.info("This is main thread running");

        log.info("-------------------------------------");
        Executor02 executor02 = new Executor02();
        executor02.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("This is executor02 thread running");
        });
        log.info("This is main thread running");
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Executor01 implements Executor {

        /**
         * 默认是同步的
         * @param command
         */
        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }

    private static class Executor02 implements Executor {

        /**
         * 每次新建线程运行
         * 注意用的是线程start，而不是run
         * @param command
         */
        @Override
        public void execute(Runnable command) {
            new Thread(command, "executor02").start();
        }
    }
}

package com.xicheng.concurrent.mashibing;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 工作窃取线程池
 * 底层实现是ForkJoinPool，参见{@link Executors#newWorkStealingPool()}
 *
 * @author xichengxml
 * @date 2019-10-06 00:11
 */
@Slf4j
public class C52_WorkStealingPool {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newWorkStealingPool();
        log.info("core cnt: {}", Runtime.getRuntime().availableProcessors());

        // 这里要比处理器核心数多，证明后台线程可以不断运行
        executorService.submit(new R(1000));
        executorService.submit(new R(2000));
        executorService.submit(new R(2000));
        executorService.submit(new R(2000));
        executorService.submit(new R(2000));

        // 由于产生的是精灵线程（守护线程，后台线程），主线程阻塞等待输出
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class R implements Runnable {

        private int time;

        public R(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(Thread.currentThread().getName() + ":" + time);
        }
    }
}

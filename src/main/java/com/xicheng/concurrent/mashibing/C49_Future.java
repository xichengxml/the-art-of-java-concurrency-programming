package com.xicheng.concurrent.mashibing;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 认识{@link java.util.concurrent.Future}
 *
 * @author xichengxml
 * @date 2019-10-03 16:54
 */
@Slf4j
public class C49_Future {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        for (int i = 0; i < 6; i++) {
            Future<String> future = executorService.submit(() -> Thread.currentThread().getName());
            try {
                log.info(i + ": " + future.get() + ": " + future.isCancelled() + "-" + future.isDone());
            } catch (Exception e) {
                log.error("error");
            }
        }

        log.info("--------------------------------------------------------");
        // 确保分割线先打印
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 6; i++) {
            Future<String> future = executorService.submit(() -> {
                TimeUnit.SECONDS.sleep(1);
                return Thread.currentThread().getName();
            });
            if (i % 2 == 1) {
                try {
                    // 对比二者的结果
                    // future.cancel(true);
                    future.cancel(false);
                } catch (Exception e) {
                    log.error("error: {}", e.getMessage());
                }
            }
            try {
                log.info(i + ": " + future.isCancelled() + "-" + future.isDone() + ": " + future.get());
            } catch (Exception e) {
                log.error("error: {}", future.isCancelled());
            }
        }
    }
}

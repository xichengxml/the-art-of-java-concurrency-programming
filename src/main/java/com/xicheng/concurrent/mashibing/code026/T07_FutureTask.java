package com.xicheng.concurrent.mashibing.code026;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 认识{@link java.util.concurrent.FutureTask}
 * 这个要怎么用
 *
 * @author xichengxml
 * @date 2019-10-03 19:40
 */
@Slf4j
public class T07_FutureTask {

    public static void main(String[] args) {
        FutureTask<String> stringFutureTask = new FutureTask<>(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                log.error("error: {}", e.getMessage());
            }
            return Thread.currentThread().getName();
        });

        // 一定要放到线程里，否则不执行
        new Thread(stringFutureTask).start();

        try {
            // 阻塞等待
            String s = stringFutureTask.get();
            log.info(s);
        } catch (Exception e) {
            log.error("error: {}", e.getMessage());
        }
    }
}

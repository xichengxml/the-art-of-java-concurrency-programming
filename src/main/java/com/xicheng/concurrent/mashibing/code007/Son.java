package com.xicheng.concurrent.mashibing.code007;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author xichengxml
 * @date 2019-08-31 06:06
 */
@Slf4j
public class Son extends Father {
    @Override
    public synchronized void m() {
        log.info("som m start...");
        // 在这睡1秒，保证打印的顺序
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.m();
        // 保证打印的顺序
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("son m end");
    }
}

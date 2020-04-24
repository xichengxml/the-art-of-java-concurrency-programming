package com.xicheng.concurrent.mashibing.code016;

import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 回顾synchronized用法
 *
 * @author xichengxml
 * @date 2019-09-07 16:10
 */
@Slf4j
public class ReentrantLock01 {

    private synchronized void m1() {
        for (int i = 0; i < 10; i++) {
            log.info("m1: {}", i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void m2() {
        log.info("m2");
    }

    public static void main(String[] args) throws Exception {
        ReentrantLock01 instance = new ReentrantLock01();
	    ThreadPoolUtil.executeThread(instance::m1);

        // 保证t1先被执行
	    TimeUnit.SECONDS.sleep(1);
        ThreadPoolUtil.executeThread(instance::m2);
    }
}

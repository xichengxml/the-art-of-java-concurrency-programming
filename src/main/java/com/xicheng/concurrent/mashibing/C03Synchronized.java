package com.xicheng.concurrent.mashibing;

import com.xicheng.concurrent.mashibing.common.ConcurrentConstant;
import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import com.xicheng.concurrent.util.LogUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author xichengxml
 * @description
 * @date 2022-03-19 10:28:58
 */
@Slf4j
public class C03Synchronized {

    private static int cnt = 1000;

    private void decrease01() {
        synchronized (C03Synchronized.class) {
            cnt--;
            LogUtil.info(log, "decrease01 thread name: {}, count: {}", Thread.currentThread().getName(), cnt);
        }
    }

    /**
     * 对比
     */
    private void decrease02() {
        synchronized (this) {
            cnt--;
            LogUtil.info(log, "decrease02 thread name: {}. count: {}", Thread.currentThread().getName(), cnt);
        }
    }

    /**
     * 这种情况下decrease01能够保证原子性，decrease02不能
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < ConcurrentConstant.CNT; i++) {
            C03Synchronized example = new C03Synchronized();
            ThreadPoolUtil.executeThread(example::decrease01);
        }
        TimeUnit.SECONDS.sleep(5);
        log.info("-----------------------------------------------------------------------");
        for (int i = 0; i < ConcurrentConstant.CNT; i++) {
            C03Synchronized example = new C03Synchronized();
            ThreadPoolUtil.executeThread(example::decrease02);
        }
    }
}

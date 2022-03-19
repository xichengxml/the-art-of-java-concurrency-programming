package com.xicheng.concurrent.mashibing;

import com.xicheng.concurrent.mashibing.common.ConcurrentConstant;
import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import com.xicheng.concurrent.util.LogUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * synchronized关键字
 * 对某个对象加锁
 *
 * @author xichengxml
 * @date 2019-08-30 17:06:05
 */
@Slf4j
public class C01Synchronized {

    private int cnt = 10;

    private final Object object = new Object();

    public static void main(String[] args) throws Exception {
        C01Synchronized synchronizedExample = new C01Synchronized();
        for (int i = 0; i < ConcurrentConstant.CNT; i++) {
            ThreadPoolUtil.executeThread(synchronizedExample::decrease01);
        }
        TimeUnit.SECONDS.sleep(5);
        log.info("------------------------------------------------------");
        synchronizedExample = new C01Synchronized();
        for (int i = 0; i < ConcurrentConstant.CNT; i++) {
            ThreadPoolUtil.executeThread(synchronizedExample::decrease02);
        }
        TimeUnit.SECONDS.sleep(5);
        log.info("------------------------------------------------------");
        synchronizedExample = new C01Synchronized();
        for (int i = 0; i < ConcurrentConstant.CNT; i++) {
            ThreadPoolUtil.executeThread(synchronizedExample::decrease03);
        }
    }

    private void decrease01() {
        // 任何线程想要执行以下代码，必须拿到object的锁
        synchronized (object) {
            cnt--;
            LogUtil.info(log, "decrease01 Thread name: {}, count: {}", Thread.currentThread().getName(), cnt);
        }
    }

    private void decrease02() {
        // 任何线程想要执行以下代码，必须拿到object的锁
        synchronized (this) {
            cnt--;
            LogUtil.info(log, "decrease02 Thread name: {}, count: {}", Thread.currentThread().getName(), cnt);
        }
    }

    private synchronized void decrease03() {
        cnt--;
        LogUtil.info(log, "decrease03 Thread name: {}, count: {}", Thread.currentThread().getName(), cnt);
    }
}

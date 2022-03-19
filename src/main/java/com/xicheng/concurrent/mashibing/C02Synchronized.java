package com.xicheng.concurrent.mashibing;

import com.xicheng.concurrent.mashibing.common.ConcurrentConstant;
import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import com.xicheng.concurrent.util.LogUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
* @description
* @author xichengxml
* @date 2022-03-19 10:04:19
*/
@Slf4j
public class C02Synchronized {

    private static int cnt = 1000;

    private static synchronized void decrease01() {
        cnt--;
        LogUtil.info(log, "decrease01 thread name: {}, count: {}", Thread.currentThread().getName(), cnt);
    }

    private void decrease02() {
        synchronized (C02Synchronized.class) {
            cnt--;
            LogUtil.info(log, "decrease02 thread name: {}, count: {}", Thread.currentThread().getName(), cnt);
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < ConcurrentConstant.CNT; i++) {
            ThreadPoolUtil.executeThread(C02Synchronized::decrease01);
        }
        TimeUnit.SECONDS.sleep(5);
        for (int i = 0; i < ConcurrentConstant.CNT; i++) {
            C02Synchronized example = new C02Synchronized();
            ThreadPoolUtil.executeThread(example::decrease02);
        }
    }
}

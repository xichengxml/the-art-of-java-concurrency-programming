package com.xicheng.concurrent.mashibing.code008;

import com.xicheng.concurrent.mashibing.common.TestCaseException;
import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 程序在执行过程中，如果遇到异常，默认锁会被释放
 * 所以，在并发的过程中，一定要注意异常，不然可能会发生数据不一致的情况
 * 比如，在一个web app处理过程中，多个servlet线程共同访问同一个资源，这时如果异常处理不合适，
 * 在第一个线程抛出异常，其他线程就会进入同步代码区，有可能会访问到异常产生时的数据
 * 因此要非常小心地处理同步业务逻辑中的异常
 *
 * @author xichengxml
 * @date 2019-08-31 07:38
 */
@Slf4j
public class T {

    private int cnt = 0;

    private synchronized void m() {
        log.info(Thread.currentThread().getName() + " start...");
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (cnt == 5) {
                // 此处抛出异常，锁将被释放，要想不被释放，可以在这里进行catche，然后让循环继续
	            throw new TestCaseException("在这抛出异常");
            }
            log.info("cnt: {}", cnt);
            cnt++;
        }
    }

    private synchronized int getCnt() {
        return cnt;
    }

    public static void main(String[] args) {
        T t = new T();
	    ThreadPoolUtil.executeThread(t::m);
        // 保证线程启动顺序
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 预期结果是10.实际是5
        log.info("cnt result: {}", t.getCnt());
    }
}

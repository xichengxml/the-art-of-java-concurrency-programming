package com.xicheng.concurrent.mashibing.code016;

import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock时可以使用tryLock尝试获取锁
 *
 * @author xichengxml
 * @date 2019-09-07 16:23
 */
@Slf4j
public class ReentrantLock03 {

    private ReentrantLock lock = new ReentrantLock();

    private void m1() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                log.info("m1: {}", i);
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void m2() {
        boolean locked = false;
        try {
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            log.info("m2");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ReentrantLock03 instance = new ReentrantLock03();
	    ThreadPoolUtil.executeThread(instance::m1);
	    TimeUnit.SECONDS.sleep(1);
        ThreadPoolUtil.executeThread(instance::m2);
    }
}

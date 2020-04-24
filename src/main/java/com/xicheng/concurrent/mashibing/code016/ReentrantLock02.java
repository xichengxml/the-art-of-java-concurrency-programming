package com.xicheng.concurrent.mashibing.code016;

import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用ReentrantLock实现
 * <p>
 * 注意：synchronized出现异常时jvm会自动释放锁，但是ReentrantLock需要手动释放
 *
 * @author xichengxml
 * @date 2019-09-07 16:15
 */
@Slf4j
public class ReentrantLock02 {

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
        lock.lock();
	    try {
		    log.info("m2");
	    } catch (Exception e) {
		    log.error("error: ", e);
	    } finally {
		    lock.unlock();
	    }
    }

    public static void main(String[] args) throws Exception {
        ReentrantLock02 instance = new ReentrantLock02();
	    ThreadPoolUtil.executeThread(instance::m1);
	    TimeUnit.SECONDS.sleep(1);
        ThreadPoolUtil.executeThread(instance::m2);
    }
}


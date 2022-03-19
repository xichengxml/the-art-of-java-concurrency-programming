package com.xicheng.concurrent.mashibing;

import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 同步方法和非同步方法是否可以同时调用
 * 思考：两个同步方法是否互相影响
 *
 * @author liubin52
 * @date 2019-08-30 18:12:15
 */
@Slf4j
public class C06Synchronized {

	private synchronized void print01() {
	    log.info("synchronized method start");
		try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("synchronized method end");
	}

	private void print02() {
        log.info("non synchronized method start");
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        log.info("non synchronized method end");
	}

    /**
     * 测试两种情况：一种是对象先被获取到锁，看看这种情况下是否还可以执行非同步方法（不影响）
     * 一种是对象调用非同步方法的过程中，是否影响同步方法的执行（不影响）
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception{
		C06Synchronized synchronizedExample = new C06Synchronized();
        ThreadPoolUtil.executeThread(synchronizedExample::print01);
        // 保证synchronized方法先执行
        TimeUnit.SECONDS.sleep(1);
        ThreadPoolUtil.executeThread(synchronizedExample::print02);

        // 非synchronized方法先执行
        TimeUnit.SECONDS.sleep(20);
        log.info("-----------------------------------------------------------");
        ThreadPoolUtil.executeThread(synchronizedExample::print02);
        TimeUnit.SECONDS.sleep(1);
        ThreadPoolUtil.executeThread(synchronizedExample::print01);

	}
}

package com.xicheng.concurrent.mashibing;

import com.xicheng.concurrent.mashibing.common.ConcurrentConstant;
import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import com.xicheng.concurrent.util.LogUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * description 线程安全
 *
 * @author xichengxml
 * @date 2019-08-30 18:07:08
 */
@Slf4j
public class C05Synchronized implements Runnable {

	private int cnt = 1000;

	@Override
	public synchronized void run() {
		cnt--;
        LogUtil.info(log, "thread name: {}", Thread.currentThread().getName(), cnt);
	}

	public static void main(String[] args) {
		C05Synchronized synchronizedThread = new C05Synchronized();
		for (int i = 0; i < ConcurrentConstant.CNT; i++) {
			ThreadPoolUtil.executeThread(synchronizedThread);
		}
	}
}

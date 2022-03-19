package com.xicheng.concurrent.mashibing;

import com.xicheng.concurrent.mashibing.common.ConcurrentConstant;
import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import com.xicheng.concurrent.util.LogUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 分析一下输出：非线程安全
 *
 * @author xichengxml
 * @date 2019-08-30 18:02:08
 */
@Slf4j
public class C04Synchronized implements Runnable {

	private int cnt = 1000;

	@Override
	public void run() {
		cnt--;
		LogUtil.info(log,"thread name: {}, count: {}", Thread.currentThread().getName(), cnt);
	}

	public static void main(String[] args) {
		C04Synchronized synchronizedThread = new C04Synchronized();
		for (int i = 0; i < ConcurrentConstant.CNT; i++) {
			ThreadPoolUtil.executeThread(synchronizedThread);
		}
	}
}

package com.xicheng.concurrent.mashibing;

import com.xicheng.concurrent.util.LogUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author xichengxml
 * @date 2019-08-30 18:07:08
 */
@Slf4j
public class C06_Synchronized implements Runnable {

	private int cnt = 10;

	@Override
	public synchronized void run() {
		cnt--;
        LogUtil.info(log, "thread name: {}", Thread.currentThread().getName(), cnt);
	}

	public static void main(String[] args) {
		C06_Synchronized synchronizedThread = new C06_Synchronized();
		for (int i = 0; i < 10; i++) {
			new Thread(synchronizedThread).start();
		}
	}
}

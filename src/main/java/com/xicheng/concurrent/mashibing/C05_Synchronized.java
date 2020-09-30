package com.xicheng.concurrent.mashibing;

import com.xicheng.concurrent.util.LogUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 分析一下输出
 *
 * @author xichengxml
 * @date 2019-08-30 18:02:08
 */
@Slf4j
public class C05_Synchronized implements Runnable {

	private int cnt = 10;

	@Override
	public void run() {
		cnt--;
		LogUtil.info(log,"thread name: {}, count: {}", Thread.currentThread().getName(), cnt);
	}

	public static void main(String[] args) {
		C05_Synchronized synchronizedThread = new C05_Synchronized();
		for (int i = 0; i < 10; i++) {
			new Thread(synchronizedThread).start();
		}
	}
}

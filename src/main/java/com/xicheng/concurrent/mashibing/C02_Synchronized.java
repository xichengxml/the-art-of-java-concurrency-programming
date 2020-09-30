package com.xicheng.concurrent.mashibing;

import com.xicheng.concurrent.util.LogUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author liubin52
 * @date 2019-08-30 17:13:02
 */
@Slf4j
public class C02_Synchronized {

	private int cnt = 10;

	/**
	 *
	 */
	private void decrease() {
		// 任何线程想要执行以下代码，必须先拿到this的锁
		synchronized (this) {
			cnt--;
			LogUtil.info(log, "decrease thread name: {}, count: {}", Thread.currentThread().getName(), cnt);
		}
	}

	public static void main(String[] args) {
		C02_Synchronized synchronizedExample = new C02_Synchronized();
		for (int i = 0; i < 10; i++) {
			synchronizedExample.decrease();
		}
	}
}

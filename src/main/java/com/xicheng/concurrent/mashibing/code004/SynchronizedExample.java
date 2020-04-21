package com.xicheng.concurrent.mashibing.code004;

import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 同步方法和非同步方法是否可以同时调用
 * @author xichengxml
 * @date 2019-08-30 18:12:15
 */
@Slf4j
public class SynchronizedExample {

	private synchronized void print01() {
		log.info("print01 start...");
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("print01 end");
	}

	private void print02() {
		log.info("print02 start...");
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("print02 end");
	}

	public static void main(String[] args) {
		SynchronizedExample synchronizedExample = new SynchronizedExample();
		ThreadPoolUtil.executeThread(synchronizedExample::print01);
		ThreadPoolUtil.executeThread(synchronizedExample::print02);
	}

}

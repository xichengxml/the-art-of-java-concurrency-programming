package com.xicheng.concurrent.mashibing.code015;

import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 实现一个容器，提供两个方法，add和size
 * 写两个线程，线程1往容器中添加10个元素，线程2监控元素的个数，当元素个数达到5个时，线程2给出预警
 * <p>
 * 实现思路1：给lists添加volatile关键字，使线程2可以及时得到通知，但是死循环比较耗费资源
 *
 * @author xichengxml
 * @date 2019-09-07 14:44
 */
@Slf4j
public class MyContainer01 {

	private volatile List<Integer> list = new ArrayList<>();

	private void add(int element) {
		list.add(element);
	}

	private int size() {
		return list.size();
	}

	public static void main(String[] args) {

		MyContainer01 container = new MyContainer01();

		ThreadPoolUtil.executeThread(() -> {
			for (int i = 0; i < 10; i++) {
				log.info("name: {}, add element: {}", Thread.currentThread().getName(), i);
				container.add(i);

				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		ThreadPoolUtil.executeThread(() -> {
			while (true) {
				if (container.size() == 5) {
					log.info("send some notification...");
					break;
				}
			}
		});
	}
}

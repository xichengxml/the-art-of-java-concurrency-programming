package com.xicheng.concurrent.mashibing.code015;

import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 实现一个容器，提供两个方法，add和size
 * 写两个线程，线程1往容器中添加10个元素，线程2监控元素的个数，当元素个数达到5个时，线程2给出预警
 *
 * 实现思路：只需要进行线程间通信，使用锁加wait-notify的机制显得过重
 * 可以使用CountDownLatch（门闩）替代，可以不涉及同步&锁
 *
 * 开启线程的顺序有没有关系？
 *
 * @author xichengxml
 * @date 2019-09-07 15:37
 */
@Slf4j
public class MyContainer04 {

    private List<Integer> list = new ArrayList<>();

    private void add(int element) {
        list.add(element);
    }

    private int size() {
        return list.size();
    }

    public static void main(String[] args) throws Exception {
        MyContainer04 container = new MyContainer04();
        CountDownLatch countDownLatch = new CountDownLatch(1);

	    ThreadPoolUtil.executeThread(() -> {
		    for (int i = 0; i < 10; i++) {
			    container.add(i);
			    log.info("name: {}, add element: {}", Thread.currentThread().getName(), i);

			    if (container.size() == 5) {
				    countDownLatch.countDown();
			    }
			    try {
				    TimeUnit.SECONDS.sleep(1);
			    } catch (InterruptedException e) {
				    e.printStackTrace();
			    }
		    }
	    });

	    TimeUnit.SECONDS.sleep(1);

		ThreadPoolUtil.executeThread(() -> {
			if (container.size() != 5) {
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 也可以指定超时时间
				// countDownLatch.await(5, TimeUnit.SECONDS);
				log.info("send some notification");
			}
		});
    }
}

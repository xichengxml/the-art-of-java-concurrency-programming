package com.xicheng.concurrent.mashibing.code015;

import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 实现一个容器，提供两个方法，add和size
 * 写两个线程，线程1往容器中添加10个元素，线程2监控元素的个数，当元素个数达到5个时，线程2给出预警
 *
 * 实现思路：使用wait-notify
 * 注意：wait释放锁，notify不释放锁；运用这种方法时，需要保证监听线程首先开启
 *
 * 执行结果：t1执行完之后t2才执行，因为notify不会释放锁
 *
 * @author xichengxml
 * @date 2019-09-07 15:06
 */
@Slf4j
public class MyContainer02 {

    private List<Integer> list = new ArrayList<>();

    private void add(int element) {
        list.add(element);
    }

    private int size() {
        return list.size();
    }

    public static void main(String[] args) throws Exception {
        final Object lock = new Object();
        MyContainer02 container = new MyContainer02();

	    ThreadPoolUtil.executeThread(() -> {
		    synchronized (lock) {
			    if (container.size() != 5) {
				    try {
					    lock.wait();
				    } catch (InterruptedException e) {
					    e.printStackTrace();
				    }
			    }
			    log.info("send some notification...");
		    }
	    });

        // 睡眠一会保证监听线程首先开启
	    TimeUnit.SECONDS.sleep(1);

	    ThreadPoolUtil.executeThread(() -> {
		    synchronized (lock) {
			    for (int i = 0; i < 10; i++) {
				    container.add(i);
				    log.info("name: {}, add element: {}", Thread.currentThread().getName(), i);
				    if (i == 5) {
					    lock.notify();
				    }
				    try {
					    TimeUnit.SECONDS.sleep(1);
				    } catch (InterruptedException e) {
					    e.printStackTrace();
				    }
			    }
		    }
	    });
    }
}

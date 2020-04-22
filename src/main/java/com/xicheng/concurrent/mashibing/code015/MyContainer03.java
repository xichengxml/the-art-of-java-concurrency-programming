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
 * 注意：notify不释放锁，所以需要在notify之后进行wait释放锁
 *
 * 在这里先开启添加线程可不可以？
 *
 * @author xichengxml
 * @date 2019-09-07 15:17
 */
@Slf4j
public class MyContainer03 {

    private List<Integer> list = new ArrayList<>();

    private void add(int element) {
        list.add(element);
    }

    private int size() {
        return list.size();
    }

    public static void main(String[] args) throws Exception {
        MyContainer03 container = new MyContainer03();

        Object lock = new Object();

        ThreadPoolUtil.executeThread(() -> {
            synchronized (lock) {
                if (container.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.info("send some notification....");
                lock.notify();
            }
        });

        TimeUnit.SECONDS.sleep(1);

        ThreadPoolUtil.executeThread(() -> {
	        synchronized (lock) {
		        for (int i = 0; i < 10; i++) {
			        container.add(i);
			        log.info("name: {}, add element: {}", Thread.currentThread().getName(), i);
			        try {
				        TimeUnit.SECONDS.sleep(1);
			        } catch (InterruptedException e) {
				        e.printStackTrace();
			        }
			        if (container.size() == 5) {
				        lock.notify();
				        try {
					        lock.wait();
				        } catch (InterruptedException e) {
					        e.printStackTrace();
				        }
			        }
		        }
	        }
        });
    }
}

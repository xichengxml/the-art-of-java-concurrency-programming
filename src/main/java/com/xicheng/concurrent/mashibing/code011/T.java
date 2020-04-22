package com.xicheng.concurrent.mashibing.code011;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 对比上一个小程序，可以用原子类解决一致性问题
 *
 * @author xichengxml
 * @date 2019-08-31 09:53
 */
@Slf4j
public class T {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private void m() {
        for (int i = 0; i < 10000; i++) {
            atomicInteger.getAndIncrement();
        }
    }

    public static void main(String[] args) {
        T t = new T();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threadList.add(new Thread(t::m, "thread" + i));
        }

        threadList.forEach(o -> o.start());

        // 在此处取值并不能保证原子性，所以原子类的取值需要自己做同步？
        log.info("{}", t.atomicInteger);
        log.info("{}", t.atomicInteger.get());

        threadList.forEach(o -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // 为什么不用get方法也能原子性获取？因为此处等待所有线程完成了，如果不等待线程，直接取值，两种方法都不能保证取值的原子性
        log.info("{}", t.atomicInteger);
        // 为什么get方法没有加锁也能原子性获取
        log.info("{}", t.atomicInteger.get());
    }
}

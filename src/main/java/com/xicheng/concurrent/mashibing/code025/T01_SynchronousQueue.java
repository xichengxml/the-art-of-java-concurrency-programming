package com.xicheng.concurrent.mashibing.code025;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author xichengxml
 * @date 2019-09-28 17:10
 */
public class T01_SynchronousQueue {

    public static void main(String[] args) throws Exception {

        // 默认容量为0
        BlockingQueue<String> stringBlockingQueue = new SynchronousQueue<>();
        // 单独启动一个线程，避免阻塞主线程导致下面代码无法执行
        new Thread(() -> {
            try {
                // 阻塞等待消费者消费
                stringBlockingQueue.put("value1");
                stringBlockingQueue.put("value2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        // 睡眠一会保证t1先执行
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(stringBlockingQueue.size());

        new Thread(() -> {
            try {
                System.out.println(stringBlockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}

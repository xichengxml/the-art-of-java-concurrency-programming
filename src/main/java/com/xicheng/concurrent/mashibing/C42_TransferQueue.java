package com.xicheng.concurrent.mashibing;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

/**
 * take方法会阻塞，等待队列非空时再消费
 *
 * @author xichengxml
 * @date 2019-09-28 17:04
 */
public class C42_TransferQueue {

    public static void main(String[] args) {
        LinkedTransferQueue<String> stringLinkedTransferQueue = new LinkedTransferQueue<>();
        new Thread(() -> {
            try {
                // 这里的延时是为了保证t1在t2之后消费
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread().getName() + "-queue size: " + stringLinkedTransferQueue.size());
                System.out.println("t1-" + stringLinkedTransferQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        // put方法不阻塞
        stringLinkedTransferQueue.put("value1");
        stringLinkedTransferQueue.put("value2");
        stringLinkedTransferQueue.put("value3");
        stringLinkedTransferQueue.put("value4");
        stringLinkedTransferQueue.put("value5");

        for (int i = 2; i < 7; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "-queue size: " + stringLinkedTransferQueue.size());
                    System.out.println(Thread.currentThread().getName() + "-" + stringLinkedTransferQueue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "t" + i).start();
        }

    }
}

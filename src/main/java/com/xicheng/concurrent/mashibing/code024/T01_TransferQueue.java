package com.xicheng.concurrent.mashibing.code024;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;

/**
 * take方法会阻塞，等待队列非空时再消费
 *
 * @author xichengxml
 * @date 2019-09-28 17:04
 */
public class T01_TransferQueue {

    public static void main(String[] args) {
        LinkedTransferQueue<String> stringLinkedTransferQueue = new LinkedTransferQueue<>();
        new Thread(() -> {
            try {
                // 这里的延时是为了保证t1在t2之后消费
                TimeUnit.SECONDS.sleep(1);
                System.out.println("t1-" + stringLinkedTransferQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();

        stringLinkedTransferQueue.put("value1");
        stringLinkedTransferQueue.put("value2");

        new Thread(() -> {
            try {
                System.out.println("t2-" + stringLinkedTransferQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}

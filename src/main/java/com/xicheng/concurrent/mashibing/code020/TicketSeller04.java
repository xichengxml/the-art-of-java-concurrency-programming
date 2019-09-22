package com.xicheng.concurrent.mashibing.code020;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 使用ConcurrentLinkedQueue，通过cas实现原子性，性能更高
 *
 * @author xichengxml
 * @date 2019-09-22 13:56
 */
public class TicketSeller04 {

    private static Queue<String> ticketQueue = new ConcurrentLinkedDeque<>();

    static {
        for (int i = 0; i < 10000; i++) {
            ticketQueue.add("ticket" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // 想想下面这段代码，能不能用在vector或Collections.synchronized上，只通过remove()实现原子性
                while (true) {
                    String ticket = ticketQueue.poll();
                    if (ticket == null) {
                        break;
                    } else {
                        System.out.println(Thread.currentThread().getName() + ": " + ticket);
                    }
                }

            }, "t" + i).start();
        }
    }
}

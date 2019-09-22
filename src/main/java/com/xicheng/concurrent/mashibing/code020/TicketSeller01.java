package com.xicheng.concurrent.mashibing.code020;

import java.util.ArrayList;
import java.util.List;

/**
 * 有N张火车票，每张票有一个编号
 * 开启10个线程卖票
 * 请写一个模拟程序
 *
 * @author xichengxml
 * @date 2019-09-22 13:28
 */
public class TicketSeller01 {

    private static List<String> ticketList = new ArrayList<>();

    static {
        for (int i = 0; i < 10000; i++) {
            ticketList.add("ticket" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (ticketList.size() > 0) {
                    System.out.println(Thread.currentThread().getName() + ": " + ticketList.remove(0));
                }
            }, "t" + i).start();
        }
    }
}

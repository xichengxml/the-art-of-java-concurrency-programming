package com.xicheng.concurrent.mashibing.code020;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 使用synchronized保证size()和remove的原子性
 *
 * @author xichengxml
 * @date 2019-09-22 13:47
 */
public class TicketSeller03 {

    private static List<String> ticketList = new ArrayList<>();

    static {
        for (int i = 0; i < 10000; i++) {
            ticketList.add("ticket" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // 想想为什么要把while放在这里，放在synchronized语句块中行不行
                while (true) {
                    synchronized (ticketList) {
                        if (ticketList.size() <= 0) {
                            break;
                        }
                        try {
                            TimeUnit.MILLISECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + ": " + ticketList.remove(0));
                    }
                }

            }, "t" + i).start();
        }
    }
}

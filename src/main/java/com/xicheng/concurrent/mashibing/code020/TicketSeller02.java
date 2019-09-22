package com.xicheng.concurrent.mashibing.code020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * 使用Vector或者Collections.synchronized×××
 *
 *  存在问题：size() 和remove()组合方法不是原子的
 * @author xichengxml
 * @date 2019-09-22 13:34
 */
public class TicketSeller02 {

    private static Vector<String> ticketVector = new Vector<>();

    private static List<String> ticketList = Collections.synchronizedList(new ArrayList<>());

    static {
        for (int i = 0; i < 10000; i++) {
            ticketVector.add("vector" + i);
            ticketList.add("list" + i);
        }
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (ticketVector.size() > 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + ticketVector.remove(0));
                }
            }, "vector" + i).start();
        }

        TimeUnit.SECONDS.sleep(15);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (ticketList.size() > 0) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ": " + ticketList.remove(0));
                }
            }, "list" + i).start();
        }
    }
}

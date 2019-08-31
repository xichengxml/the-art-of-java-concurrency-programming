package com.xicheng.concurrent.mashibing.code005;

import java.util.concurrent.TimeUnit;

/**
 * 对业务写方法加锁
 * 业务读方法不加锁
 * 容易引起脏读问题
 *
 * @author xichengxml
 * @date 2019-08-31 04:56
 */
public class AccountExample {

    private String name;

    private double balance;

    public AccountExample(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    private synchronized void setUserBalance(double balance) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance += balance;
    }

    private double getUserBalance() {
        return this.balance;
    }

    public static void main(String[] args) throws InterruptedException {
        AccountExample accountExample = new AccountExample("zhangsan", 10);

        new Thread(() -> accountExample.setUserBalance(100), "set").start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(accountExample.getUserBalance());
        TimeUnit.SECONDS.sleep(2);
        System.out.println(accountExample.getUserBalance());
    }
}

package com.xicheng.concurrent.mashibing;

import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;

import java.util.concurrent.TimeUnit;

/**
 * 对业务写方法加锁
 * 业务读方法不加锁
 * 容易引起脏读问题
 *
 * @author xichengxml
 * @date 2019-08-31 04:56
 */
public class C08AccountExample {

    private String name;

    private double balance;

    public C08AccountExample(String name, double balance) {
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
        C08AccountExample accountExample = new C08AccountExample("zhangsan", 10);

        ThreadPoolUtil.executeThread(() -> accountExample.setUserBalance(199D));
        TimeUnit.SECONDS.sleep(1);
        System.out.println(accountExample.getUserBalance());
        TimeUnit.SECONDS.sleep(2);
        System.out.println(accountExample.getUserBalance());
    }
}

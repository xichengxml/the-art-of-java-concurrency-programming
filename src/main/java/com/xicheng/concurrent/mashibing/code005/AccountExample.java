package com.xicheng.concurrent.mashibing.code005;

import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 对业务写方法加锁
 * 业务读方法不加锁
 * 容易引起脏读问题
 *
 * @author xichengxml
 * @date 2019-08-31 04:56
 */
@Slf4j
@Setter
@Getter
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

    private /*synchronized*/ double getUserBalance() {
        return this.balance;
    }

    public static void main(String[] args) throws InterruptedException {
        AccountExample accountExample = new AccountExample("zhangsan", 10);

	    ThreadPoolUtil.executeThread(() -> accountExample.setUserBalance(100));
        TimeUnit.SECONDS.sleep(1);
        log.info("balance: {}", accountExample.getUserBalance());
        TimeUnit.SECONDS.sleep(2);
	    log.info("balance: {}", accountExample.getUserBalance());
    }
}

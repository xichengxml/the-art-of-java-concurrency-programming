package com.xicheng.concurrent.mashibing;

import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author xichengxml
 * @date 2019-08-31 06:06
 */
public class C10Son extends C10Father {

    @Override
    public synchronized void m() {
        System.out.println("som m start...");
        // 在这睡1秒，保证打印的顺序
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.m();
        // 保证打印的顺序
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("son m end");
    }
}

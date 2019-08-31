package com.xicheng.concurrent.mashibing.code010;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile并不能保证多个线程之间修改变量的不一致性问题，也就是说volatile不能替代synchronized
 * volatile只能表示可见性
 * description
 *
 * @author xichengxml
 * @date 2019-08-31 09:44
 */
public class T {

    private volatile int cnt = 0;

    private void m() {
        for (int i = 0; i < 10000; i++) {
            cnt++;
        }
    }

    public static void main(String[] args) {
        T t = new T();
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threadList.add(new Thread(t::m, "thread" + i));
        }

        threadList.forEach((o) -> o.start());

        threadList.forEach((o) -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(t.cnt);
    }
}

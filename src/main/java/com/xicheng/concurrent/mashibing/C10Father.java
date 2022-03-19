package com.xicheng.concurrent.mashibing;

        import java.util.concurrent.TimeUnit;

public class C10Father {

    public synchronized void m() {
        System.out.println("father m start...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("father m end");
    }
}

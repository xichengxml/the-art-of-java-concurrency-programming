package com.xicheng.concurrent.mashibing;

/**
 * synchronized关键字
 * 对某个对象加锁
 *
 * @author xichengxml
 * @date 2019-08-30 17:06:05
 */
public class C01_Synchronized {

    private int cnt = 10;

    private final Object object = new Object();

    public static void main(String[] args) {
        C01_Synchronized synchronizedExample = new C01_Synchronized();
        for (int i = 0; i < 10; i++) {
            new Thread(synchronizedExample::decrease).start();
        }
    }

    private void decrease() {
        // 任何线程想要执行以下代码，必须拿到object的锁
        synchronized (object) {
            cnt--;
            System.out.println("Thread name: " + Thread.currentThread().getName() + " count: " + cnt);
        }
    }
}

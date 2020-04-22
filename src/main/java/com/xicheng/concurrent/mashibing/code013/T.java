package com.xicheng.concurrent.mashibing.code013;

import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 锁对象o其中的属性发生变化不影响锁的使用，但是对象引用发生变化会影响使用，
 * 也可以间接证明锁的对象是堆内存里的对象，而不是栈内存中的引用
 * 因此，要避免在同步的过程中改变对象，锁对象最好用final关键字修饰
 *
 * @author xichengxml
 * @date 2019-08-31 10:39
 */
@Slf4j
public class T {

    private int cnt = 0;

    private synchronized void m() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info(Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        T t = new T();
	    ThreadPoolUtil.executeThread(t::m);

        // 改变对象中的某个属性
        log.info("change field");
        t.cnt++;
        ThreadPoolUtil.executeThread(t::m);

        // 改变对象引用
        log.info("change reference");
        t = new T();
        ThreadPoolUtil.executeThread(t::m);
    }
}

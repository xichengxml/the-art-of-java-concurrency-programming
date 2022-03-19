package com.xicheng.concurrent.mashibing;

import com.xicheng.concurrent.mashibing.common.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
* @description
* @author xichengxml
* @date 2022-03-19 10:53:58
*/
@Slf4j
public class C07Synchronized {

    private synchronized void print01() {
        log.info("C07Synchronized print01 start");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("C07Synchronized print01 end");
    }

    private synchronized void print02() {
        log.info("C07Synchronized print02 start");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("C07Synchronized print02 end");
    }

    public static void main(String[] args) throws Exception {
        // 同一个对象，会会涉及到抢锁
        C07Synchronized example = new C07Synchronized();
        ThreadPoolUtil.executeThread(example::print01);
        ThreadPoolUtil.executeThread(example::print02);

        TimeUnit.SECONDS.sleep(20);
        log.info("----------------------------------------------------------");

        // 不同对象互不影响，静态同步方法的话会互相影响
        C07Synchronized example01 = new C07Synchronized();
        C07Synchronized example02 = new C07Synchronized();
        ThreadPoolUtil.executeThread(example01::print01);
        ThreadPoolUtil.executeThread(example02::print02);
    }
}

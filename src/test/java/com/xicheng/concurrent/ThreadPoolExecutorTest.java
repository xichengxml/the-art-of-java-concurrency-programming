package com.xicheng.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * description
 *
 * @author xichengxml
 * @date 2020-11-26 00:04
 */
@Slf4j
public class ThreadPoolExecutorTest {

    @Test
    public void shiftTest() {
        log.info("{}", (-1 << 2));
        log.info("{}", (0 << 2));
        log.info("{}", (3 << 2));
    }

    @Test
    public void wavyLineTest() {
        log.info("{}", 5 & ~2);
    }
}

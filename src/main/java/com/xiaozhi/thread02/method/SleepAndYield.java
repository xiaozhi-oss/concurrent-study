package com.xiaozhi.thread02.method;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 */
@Slf4j
public class SleepAndYield {

    public static void main(String[] args) {
        testYield();
    }

    public static void testYield() {
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                if (i % 2 == 0) {
                    log.debug("t1 输出：{}", i);
                    Thread.yield();
                }
            }
        }, "t1").start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                if (i % 2 != 0) {
                    log.debug("t2 输出：{}", i);
                    Thread.yield();
                }
            }
        }, "t2").start();
    }
}

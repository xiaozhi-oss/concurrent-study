package com.xiaozhi.sharedmodel03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 */
@Slf4j(topic = "Test01")
public class Test01 {

    static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (Test01.class) {
                    count++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (Test01.class) {
                    count--;
                }
            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.info("count={}", count);
    }
}

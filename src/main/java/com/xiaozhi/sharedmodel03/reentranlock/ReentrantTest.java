package com.xiaozhi.sharedmodel03.reentranlock;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 *
 * 特点：可重入
 */
@Slf4j(topic = "ReentrantTest")
public class ReentrantTest {
    static Object lock = new Object();
    public static void main(String[] args) {
        synchronized (lock) {
            log.debug("main 加锁");
            m1();
        }
    }

    private static void m1() {
        synchronized (lock) {
            log.debug("m1 加锁");
            m2();
        }
    }

    private static void m2() {
        synchronized (lock) {
            log.debug("m2 加锁");
        }
    }
}

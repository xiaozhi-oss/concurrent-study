package com.xiaozhi.sharedmodel03.model;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 *
 * 顺序执行之 wait & notify
 */
@Slf4j(topic = "SequentialExecutionForWaitNotify")
public class SequentialExecutionForWaitNotify {

    static final Object LOCK = new Object();
    static boolean t2Run = false;
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (LOCK) {
                while (!t2Run) {
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("t1 执行...");
            }
        }).start();
        new Thread(() -> {
            synchronized (LOCK) {
                log.debug("t2 执行...");
                t2Run = true;
                LOCK.notifyAll();       // 唤醒其他线程
            }
        }).start();
    }
}

package com.xiaozhi.sharedmodel03.model;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author xiaozhi
 *
 * 顺序执行之 park & unpark
 */
@Slf4j(topic = "SequentialExecutionForParkUnpark")
public class SequentialExecutionForParkUnpark {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            log.debug("t1 执行");
        }, "t1");
        t1.start();
        new Thread(() -> {
            log.debug("t2 执行");
            LockSupport.unpark(t1);
        }, "t2").start();
    }
}

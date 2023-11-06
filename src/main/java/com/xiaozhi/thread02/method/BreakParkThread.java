package com.xiaozhi.thread02.method;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author xiaozhi
 *
 * 打断 park 线程
 */
@Slf4j(topic = "BreakParkThread")
public class BreakParkThread {

    public static void main(String[] args) {
        // test();
        test2();
    }

    public static void test() {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        TimeUtil.sleep(1);
        t1.interrupt();
    }

    public static void test2() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.debug("park...");
                LockSupport.park();     // 多次循环没有重置标记，park 失效
                log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
            }
        }, "t1");
        t1.start();

        TimeUtil.sleep(1);
        t1.interrupt();
    }
}

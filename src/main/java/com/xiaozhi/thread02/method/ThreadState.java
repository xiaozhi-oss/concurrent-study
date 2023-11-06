package com.xiaozhi.thread02.method;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 */
@Slf4j(topic = "ThreadState")
public class ThreadState {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("t1...");
        });

        Thread t2 = new Thread(() -> {
            while (true) {

            }
        });
        t2.start();

        Thread t3 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t3.start();

        Thread t4 = new Thread(() -> {
            synchronized (ThreadState.class) {
                TimeUtil.sleep(10000000);
            }
        });
        t4.start();

        Thread t5 = new Thread(() -> {
            synchronized (ThreadState.class) {
                TimeUtil.sleep(10000000);
            }
        });
        t5.start();

        Thread t6 = new Thread(() -> {
            log.debug("welcome...");
        });
        t6.start();

        log.debug("六种线程状态如下：");
        log.debug(String.valueOf(t1.getState()));
        log.debug(String.valueOf(t2.getState()));
        log.debug(String.valueOf(t3.getState()));
        log.debug(String.valueOf(t4.getState()));
        log.debug(String.valueOf(t5.getState()));
        log.debug(String.valueOf(t6.getState()));
    }
}

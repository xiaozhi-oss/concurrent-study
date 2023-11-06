package com.xiaozhi.thread02.method;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 */
@Slf4j
public class JoinTest {

    static int r1 = 0;
    static int r2 = 0;

    public static void main(String[] args) throws InterruptedException {
        // test();
        test2();
    }

    private static void test() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("========== t1 start ==========");
            TimeUtil.sleep(1);      // 休眠一秒
            r1 = 10;
            log.debug("========== t1 end ==========");
        }, "t1");
        Thread t2 = new Thread(() -> {
            log.debug("========== t2 start ==========");
            TimeUtil.sleep(1);      // 休眠一秒
            r2 = 20;
            log.debug("========== t2 end ==========");
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("r1的值为：{}", r1);
        log.debug("r2的值为：{}", r2);
    }

    static Thread t1;
    static Thread t2;

    private static void test2() throws InterruptedException {
        t1 = new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                if (i % 2 == 0) {
                    try {
                        log.debug(String.valueOf(i));
                        t2.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, "t1");
        t2 = new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                if (i % 2 != 0) {
                    try {
                        log.debug(String.valueOf(i));
                        t1.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
    }
}

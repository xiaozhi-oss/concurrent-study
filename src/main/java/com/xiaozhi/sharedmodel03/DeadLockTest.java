package com.xiaozhi.sharedmodel03;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 *
 * 测试死锁
 */
@Slf4j(topic = "DeadLockTest")
public class DeadLockTest {

    private final static Object A = new Object();
    private final static Object B = new Object();

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (A) {
                log.debug("获取到A锁");
                TimeUtil.sleep(1);
                synchronized (B) {
                    log.debug("获取到B锁");
                }
            }
        }, "t1").start();
        new Thread(() -> {
            synchronized (B) {
                log.debug("获取到B锁");
                TimeUtil.sleep(2);
                synchronized (A) {
                    log.debug("获取到A锁");
                }
            }
        }, "t2").start();
    }
}

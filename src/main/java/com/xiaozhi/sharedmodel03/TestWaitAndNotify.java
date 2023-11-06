package com.xiaozhi.sharedmodel03;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 */
@Slf4j(topic = "TestWaitAndNotify")
public class TestWaitAndNotify {

    final static Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            log.debug("执行...");
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log.debug("其他代码...");
        }, "t1").start();
        new Thread(() -> {
            log.debug("执行...");
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            log.debug("其他代码...");
        }, "t2").start();
        TimeUtil.sleep(1);
        synchronized (lock) {
            // lock.notify();          // 随机唤醒一个线程
            lock.notifyAll();    // 唤醒全部线程
        }
    }
}

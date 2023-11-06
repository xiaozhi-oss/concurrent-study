package com.xiaozhi.sharedmodel03.reentranlock;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaozhi
 */
@Slf4j(topic = "LockInterruptiblyTest")
public class LockInterruptiblyTest{
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            log.debug("启动...");
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("等锁的过程中被打断...");
                return;
            }
            log.debug("执行其他代码...");
        }, "t1");
        lock.lock();
        log.debug("获取锁...");
        t1.start();
        // TimeUtil.sleep(0.5);
        // lock.unlock();
        try {
            TimeUtil.sleep(1);
            t1.interrupt();
            log.debug("执行打断...");
        } finally {
            lock.unlock();
        }
    }
}

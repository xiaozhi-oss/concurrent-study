package com.xiaozhi.sharedmodel03.reentranlock;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaozhi
 */
@Slf4j(topic = "TryLockTest")
public class TryLockTest {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            log.debug("启动...");
            try {
                if (!lock.tryLock(2, TimeUnit.SECONDS)) {
                    log.debug("立刻获取失败...");
                    return;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                log.debug("t1 获取了锁...");
            } finally {
                lock.unlock();
            }
        }, "t1");
        lock.lock();
        log.debug("main 获得了锁");
        t1.start();
        try {
            TimeUtil.sleep(1);
        } finally {
            lock.unlock();
        }
    }
}

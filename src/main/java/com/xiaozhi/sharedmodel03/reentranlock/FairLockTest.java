package com.xiaozhi.sharedmodel03.reentranlock;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaozhi
 *
 * 公平锁测试
 */
@Slf4j(topic = "FairLockTest")
public class FairLockTest {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();
        for (int i = 0; i <  500; i++) {
            new Thread(() -> {
                lock.lock();    // 获取锁
                try {
                    log.debug("{} running...", Thread.currentThread().getName());
                } finally {
                    lock.unlock();
                }
            }, "t" + i).start();
        }
        TimeUtil.sleep(1);
        new Thread(() -> {
            log.debug("{} start...", Thread.currentThread().getName());
            lock.lock();
            try {
                log.debug("{} running...", Thread.currentThread().getName());
            } finally {
                lock.unlock();
            }
        }, "强制插入").start();
        lock.unlock();
    }
}

package com.xiaozhi.sharedmodel03.reentranlock;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaozhi
 */
@Slf4j(topic = "LockAndUnlockTest")
public class LockAndUnlockTest {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                TimeUtil.sleep(1);
                log.debug("执行任务...");
            } finally {
                lock.unlock();
            }
        });
        t1.start();
        TimeUtil.sleep(0.5);
        lock.lock();
        try {
            log.debug("main 执行");
        } finally {
            lock.unlock();
        }
    }
}

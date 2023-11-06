package com.xiaozhi.sharedmodel03.reentranlock;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaozhi
 *
 * 买烟和买早餐
 */
@Slf4j(topic = "AwaitTest")
public class AwaitTest {

    static ReentrantLock lock = new ReentrantLock();
    static Condition waitCigaretteQueue = lock.newCondition();
    static Condition waitBreakfastQueue = lock.newCondition();
    static boolean hasCigarette = false;
    static boolean hasBreakfast = false;

    public static void main(String[] args) {
        new Thread(() -> {
            lock.lock();
            try {
                while (!hasCigarette) {
                    waitCigaretteQueue.await();
                }
                log.debug("烟送到了...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "t1").start();
        new Thread(() -> {
            lock.lock();
            try {
                while (!hasBreakfast) {
                    waitBreakfastQueue.await();
                }
                log.debug("早餐送到了...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "t2").start();
        TimeUtil.sleep(1);
        sendCigarette();
        TimeUtil.sleep(1);
        sendBreakfast();
    }

    private static void sendBreakfast() {
        lock.lock();
        try {
            hasBreakfast = true;
            waitBreakfastQueue.signalAll();
            log.debug("早餐送出去了...");
        } finally {
            lock.unlock();
        }
    }

    private static void sendCigarette() {
        lock.lock();
        try {
            hasCigarette = true;
            waitCigaretteQueue.signalAll();
            log.debug("烟送出去了...");
        } finally {
            lock.unlock();
        }
    }
}

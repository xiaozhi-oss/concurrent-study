package com.xiaozhi.sharedmodel03.model;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaozhi
 */
public class AlternatingOutputForReentranLock {

    public static void main(String[] args) {
        AwaitSignal awaitSignal = new AwaitSignal(5);
        Condition c1 = awaitSignal.newCondition();
        Condition c2 = awaitSignal.newCondition();
        Condition c3 = awaitSignal.newCondition();
        new Thread(() -> { awaitSignal.print("a", c1, c2); }).start();
        new Thread(() -> { awaitSignal.print("b", c2, c3); }).start();
        new Thread(() -> { awaitSignal.print("c", c3, c1); }).start();
        awaitSignal.start(c1);      // 唤醒c1
    }
}

class AwaitSignal extends ReentrantLock {

    private final int loopNum;

    public AwaitSignal(int loopNum) {
        this.loopNum = loopNum;
    }
    // 一开始多个线程都再等待，所以需要先唤醒一个线程才能往下执行
    public void start(Condition condition) {
        lock();
        try {
            condition.signalAll();
        } finally {
            lock();
        }
    }

    public void print(String str, Condition cur, Condition next) {
        lock();     // 获取锁
        try {
            cur.await();
            System.out.print(str);
            next.signalAll();       // 唤醒下一个条件变量的线程
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            unlock();
        }
    }
}

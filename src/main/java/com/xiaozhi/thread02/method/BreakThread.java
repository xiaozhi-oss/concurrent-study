package com.xiaozhi.thread02.method;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 *
 * 打断线程
 * interrupt
 * interrupted
 * isInterrupted
 */
@Slf4j
public class BreakThread {

    public static void main(String[] args) {
        // BreakSleep();
        BreakNormalThread();
    }

    public static void BreakSleep() {
        Thread t1 = new Thread(() -> {
            TimeUtil.sleep(2);
        }, "t1");
        t1.start();
        TimeUtil.sleep(1);  // 休眠1秒是因为 t1 是异步执行的，所以要防止main线程执行完t1线程还没启动
        t1.interrupt();
        log.debug("打断状态：{}", t1.isInterrupted());
    }

    public static void BreakNormalThread() {
        Thread t2 = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                boolean interrupted = current.isInterrupted();
                if (interrupted) {
                    log.debug("(isInterrupted) 打断状态：{}", interrupted);
                    log.debug("(interrupted) 打断状态：{}", Thread.interrupted());
                    break;
                }
            }
        }, "t2");
        t2.start();
        TimeUtil.sleep(1);  // 休眠1秒是因为 t1 是异步执行的，所以要防止main线程执行完t1线程还没启动
        t2.interrupt();
    }
}

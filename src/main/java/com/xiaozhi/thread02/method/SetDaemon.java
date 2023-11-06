package com.xiaozhi.thread02.method;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 *
 * 设置守护线程
 */
@Slf4j(topic = "SetDaemon")
public class SetDaemon {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("t1 start...");
            TimeUtil.sleep(2);
            log.debug("线程 {} end...", Thread.currentThread().getName());
        }, "t1");
        Thread t2 = new Thread(() -> {
            log.debug("t2 start...");
            TimeUtil.sleep(3);
            log.debug("线程 {} end...", Thread.currentThread().getName());
        }, "t2");
        t1.start();
        t2.setDaemon(true);
        t2.start();
        log.debug("线程 {} end...", Thread.currentThread().getName());
    }
}

package com.xiaozhi.sharedmodel03;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 */
@Slf4j(topic = "SleepAndWaitTest")
public class SleepAndWaitTest {
    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                while (!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                log.debug("可以开始干活了");
            }
        }, "小南").start();
        new Thread(() -> {
            synchronized (room) {
                log.debug("有外卖没？[{}]", hasTakeout);
                while (!hasCigarette) {
                    log.debug("没外卖，先歇会！");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("有外卖没？[{}]", hasTakeout);
                log.debug("可以开始送外卖了...");
            }
        }, "小女").start();
        TimeUtil.sleep(1);
        new Thread(() -> {
            synchronized (room) {
                hasTakeout = true;
                log.debug("外卖到了");
                // room.notify();
                room.notifyAll();
            }
        }, "送外卖的").start();
    }
}

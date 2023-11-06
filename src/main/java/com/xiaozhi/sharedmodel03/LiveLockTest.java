package com.xiaozhi.sharedmodel03;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 *
 * 活锁
 */
@Slf4j(topic = "LiveLockTest")
public class LiveLockTest {

    static volatile Integer COUNT = 10;

    public static void decrease() {
        while (COUNT > 0) {
            TimeUtil.sleep(0.2);
            COUNT--;
            log.debug("当前 count={}", COUNT);
        }
    }

    public static void increase() {
        while (COUNT <= 20) {
            TimeUtil.sleep(0.2);
            COUNT++;
            log.debug("当前 count={}", COUNT);
        }
    }

    public static void main(String[] args) {
        new Thread(LiveLockTest::decrease, "t1").start();
        new Thread(LiveLockTest::increase, "t2").start();
    }
}

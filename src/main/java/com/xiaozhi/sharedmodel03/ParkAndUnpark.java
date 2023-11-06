package com.xiaozhi.sharedmodel03;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author xiaozhi
 */
@Slf4j(topic = "ParkAndUnpark")
public class ParkAndUnpark {

    public static void main(String[] args) {
        // test01();
        test02();
    }
    public static void test01() {
        Thread t1 = new Thread(() -> {
            log.debug("start...");
            TimeUtil.sleep(1);
            LockSupport.park();
            log.debug("继续执行...");
        }, "t1");
        t1.start();
        TimeUtil.sleep(2);
        log.debug("unpark...");
        LockSupport.unpark(t1);
    }
    public static void test02() {
        Thread t1 = new Thread(() -> {
            log.debug("start...");
            TimeUtil.sleep(2);
            LockSupport.park();
            log.debug("继续执行...");
        }, "t1");
        t1.start();
        TimeUtil.sleep(1);
        log.debug("unpark...");
        LockSupport.unpark(t1);
    }
}



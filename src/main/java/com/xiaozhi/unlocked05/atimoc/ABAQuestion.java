package com.xiaozhi.unlocked05.atimoc;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author xiaozhi
 *
 * ABA 问题
 */
@Slf4j(topic = "ABAQuestion")
public class ABAQuestion {
    static AtomicReference<String> CODE = new AtomicReference<>("A");

    public static void main(String[] args) {
        String prev = CODE.get();
        other();
        TimeUtil.sleep(1);
        log.debug("A -> C：{}", CODE.compareAndSet(prev, "C"));
    }

    private static void other() {
        new Thread(() -> {
            log.debug("A -> B：{}", CODE.compareAndSet(CODE.get(), "B"));
        }, "t1").start();
        TimeUtil.sleep(0.5);
        new Thread(() -> {
            log.debug("B -> A：{}", CODE.compareAndSet(CODE.get(), "A"));
        }, "t1").start();
    }
}

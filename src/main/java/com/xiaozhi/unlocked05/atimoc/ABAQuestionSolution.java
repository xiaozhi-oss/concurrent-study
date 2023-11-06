package com.xiaozhi.unlocked05.atimoc;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author xiaozhi
 *
 * ABA 问题解决
 */
@Slf4j(topic = "ABAQuestion")
public class ABAQuestionSolution {
    static AtomicStampedReference<String> CODE = new AtomicStampedReference<>("A", 0);

    public static void main(String[] args) {
        int stamp = CODE.getStamp();
        other();
        TimeUtil.sleep(1);
        log.debug("更新版本为：{}", stamp);
        log.debug("A -> C：{}", CODE.compareAndSet(CODE.getReference(), "C", stamp, stamp + 1));
    }

    private static void other() {
        new Thread(() -> {
            int stamp = CODE.getStamp();
            log.debug("更新版本为：{}", stamp);
            log.debug("A -> B：{}", CODE.compareAndSet(CODE.getReference(), "B", stamp, stamp + 1));
        }, "t1").start();
        TimeUtil.sleep(0.5);
        new Thread(() -> {
            int stamp = CODE.getStamp();
            log.debug("更新版本为：{}", stamp);
            log.debug("B -> A：{}", CODE.compareAndSet(CODE.getReference(), "A", stamp, stamp + 1));
        }, "t2").start();
    }
}

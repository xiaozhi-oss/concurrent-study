package com.xiaozhi.sharedmodel03.designpatterns;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 * <p>
 * 保护性暂停
 */
@Slf4j(topic = "ProtectivePause")
public class ProtectivePause {

    public static void main(String[] args) {
        GuardedObject go = new GuardedObject();
        new Thread(() -> {
            log.debug("start...");
            Object res = go.get(2000);
            log.debug("结果：{}", res);
        }, "t1").start();
        new Thread(() -> {
            log.debug("start...");
            TimeUtil.sleep(3);
            go.complete("我是个靓仔");
            log.debug("结果已传递...");
        }, "t2").start();
    }
}

@Slf4j(topic = "GuardedObject")
class GuardedObject {
    private int id;

    public GuardedObject(int id) {
        this.id = id;
    }

    public GuardedObject() {
    }

    public int getId() {
        return id;
    }

    private Object response;

    public Object get(long timeout) {
        synchronized (this) {
            // 开始时间
            long begin = System.currentTimeMillis();
            // 已经经历时间
            long timePassed = 0;
            // 不满足条件需要等待
            while (response == null) {
                // 剩余等待的时间 = 总共等待时间 - 已经经历时间
                long waitTime = timeout - timePassed;
                // 如果剩余等待时间为 0 则退出循环
                if (waitTime <= 0) {
                    log.debug("退出循环...");
                    break;
                }
                try {
                    // 只需要等待剩余的等待时间即可
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 已经经历时间
                timePassed = System.currentTimeMillis() -  begin;
            }
        }
        return this.response;
    }

    public void complete(Object response) {
        synchronized (this) {
            this.response = response;
            this.notifyAll();
        }
    }
}

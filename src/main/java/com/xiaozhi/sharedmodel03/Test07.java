package com.xiaozhi.sharedmodel03;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 */
public class Test07 {

    public static void main(String[] args) {
        Room room = new Room();
        new Thread(room::study, "t1").start();
        new Thread(room::play, "t2").start();
    }
}

@Slf4j(topic = "room")
class Room {
    private final Object LOCK = new Object();
    public void study() {
        synchronized (LOCK) {
            log.debug("开始学习...");
            TimeUtil.sleep(1);
            log.debug("结束学习...");
        }
    }
    public void play() {
        synchronized (this) {
            log.debug("开始玩耍...");
            TimeUtil.sleep(1);
            log.debug("结束玩耍...");
        }
    }
}
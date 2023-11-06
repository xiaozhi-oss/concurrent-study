package com.xiaozhi.sharedmodel03.questions;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 */
public class PhilosopherEatQuestion {

    public static void main(String[] args) {
        Chopstick c1 = new Chopstick();
        Chopstick c2 = new Chopstick();
        Chopstick c3 = new Chopstick();
        Chopstick c4 = new Chopstick();
        Chopstick c5 = new Chopstick();
        new Philosopher("苏格拉底", c1, c2).start();
        new Philosopher("柏拉图", c2, c3).start();
        new Philosopher("亚里士多德", c3, c4).start();
        new Philosopher("赫拉克利特", c4, c5).start();
        new Philosopher("阿基米德", c1, c5).start();
    }
}

@Slf4j(topic = "筷子")
class Chopstick {
}

@Slf4j(topic = "哲学家")
class Philosopher extends Thread{

    private Chopstick left;
    private Chopstick right;
    private String name;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        this.name = name;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
            // 拿到左边筷子
            synchronized (left) {
                // 拿到右边筷子
                synchronized (right) {
                    eat();
                }
            }
        }
    }

    public void eat() {
        log.debug("{} 吃上了饭", this.name);
        TimeUtil.sleep(1);
    }
}
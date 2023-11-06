package com.xiaozhi.sharedmodel03;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 */
@Slf4j(topic = "test02")
public class Test02 {

    public static void main(String[] args) throws InterruptedException {
        Option option = new Option();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                option.increase();
            }
        }, "t1");
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                option.decrease();
            }
        }, "t2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("count={}", option.getCount());
    }
}

class Option {
    private int count;

    public void increase() {
        synchronized (this) {
            count++;
        }
    }

    public void decrease() {
        synchronized (this) {
            count--;
        }
    }

    public int getCount() {
        return count;
    }
}

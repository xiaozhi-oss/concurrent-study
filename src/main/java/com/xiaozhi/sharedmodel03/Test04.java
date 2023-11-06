package com.xiaozhi.sharedmodel03;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaozhi
 */
@Slf4j
public class Test04 {
    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 200;

    public static void main(String[] args) {
        ThreadUnsafe unsafe = new ThreadSafeSubClass();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                unsafe.method01(LOOP_NUMBER);
            }, "t" + i);
        }
    }
}

class ThreadUnsafe {

    List<String> list = new ArrayList<>();
    public void method01(int loopNum) {
        for (int i = 0; i < loopNum; i++) {
            method02();
            method03();
        }
    }
    public void method02() {
        list.add("1");
    }
    public void method03() {
        list.remove(0);
    }
}

class ThreadSafeSubClass extends ThreadUnsafe {

    @Override
    public void method03() {
        new Thread(() -> {
            list.remove(0);
        }).start();
    }
}

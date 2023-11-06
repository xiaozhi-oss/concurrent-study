package com.xiaozhi.sharedmodel03;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaozhi
 */
public class Test06 {

    static final Object lock = new Object();
    static int count = 0;
    public static void main(String[] args) {
        synchronized (lock) {
            count++;
        }
    }


}


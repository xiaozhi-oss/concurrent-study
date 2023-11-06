package com.xiaozhi.utils;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaozhi
 */
public class TimeUtil {

    public static void sleep(long n) {
        try {
            TimeUnit.SECONDS.sleep(n);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sleep(double n) {
        try {
            TimeUnit.MILLISECONDS.sleep((long) (n * 1000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
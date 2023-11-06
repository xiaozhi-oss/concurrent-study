package com.xiaozhi.sharedmodel03;

/**
 * @author xiaozhi
 */
public class Test03 {

    public void test01() {
        synchronized (this) {

        }
    }

    public static void test02() {
        synchronized (Test03.class) {

        }
    }

}


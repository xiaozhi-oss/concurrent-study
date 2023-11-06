package com.xiaozhi.memory04;

/**
 * @author xiaozhi
 */
public class VolatileTest {

    public static void main(String[] args) {

    }
}

class A {
    private static int value;
    private volatile  static boolean flag;

    public void init() {
        this.value = 2;
        this.flag = true;
    }
    public void getValue() {
        if (this.flag) {
            System.out.println(this.value);
        }
    }
}

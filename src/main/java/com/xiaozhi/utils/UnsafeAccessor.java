package com.xiaozhi.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author xiaozhi
 */
public class UnsafeAccessor {

    private static final Unsafe UNSAFE;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Unsafe getUnsafe() {
        return UNSAFE;
    }
}

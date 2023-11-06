package com.xiaozhi.unlocked05.unsafe;

import com.xiaozhi.utils.UnsafeAccessor;
import sun.misc.Unsafe;

/**
 * @author xiaozhi
 */
public class MyAtomicInteger implements Account {

    private static final Unsafe UNSAFE;
    private volatile int value;
    private static final long VALUE_OFFSET;

    static {
        UNSAFE = UnsafeAccessor.getUnsafe();
        try {
            VALUE_OFFSET = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    @Override
    public int getBalance() {
        return this.value;
    }

    @Override
    public void withdraw(Integer amount) {
        int prev, next;
        do {
            prev = this.value;
            next = this.value - amount;
        } while (!UNSAFE.compareAndSwapInt(this, VALUE_OFFSET, prev, next));
    }

    public static void main(String[] args) {
        Account.demo(new MyAtomicInteger(1000_0));
    }
}

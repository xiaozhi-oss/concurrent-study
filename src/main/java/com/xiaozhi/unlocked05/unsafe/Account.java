package com.xiaozhi.unlocked05.unsafe;

import java.util.ArrayList;
import java.util.List;

public interface Account {
    // 获取余额
    int getBalance();
    // 取钱
    void withdraw(Integer amount);

    static public void demo(Account account) {
        List<Thread> list = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                account.withdraw(10);
            });
            list.add(thread);
            thread.start();
        }
        list.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("剩余金额：" + account.getBalance()
                + " coat：" + (end - start) + "ms");
    }
}
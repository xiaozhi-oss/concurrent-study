package com.xiaozhi.unlocked05;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiaozhi
 *
 * 取钱存钱问题
 */
public class AccountTest {

    public static void main(String[] args) {
        AccountUnsafe account = new AccountUnsafe(10000);
        Account.demo(account);
    }
}

class AccountUnsafe implements Account{
    private AtomicInteger balance;
    public AccountUnsafe(int balance) {
        this.balance = new AtomicInteger(balance);
    }
    @Override
    public int getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        while (true) {
            int prev = this.balance.get();
            int next = prev - amount;
            if (this.balance.compareAndSet(prev, next)) {
                break;
            }
        }
    }
}

interface Account {
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

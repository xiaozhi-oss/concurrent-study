package com.xiaozhi.unlocked05.atimoc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author xiaozhi
 */
public class AtomicReferenceTest {

    public static void main(String[] args) {
        UnsafeAccount unsafeAccount = new UnsafeAccount(new BigDecimal(10000));
        Account.demo(unsafeAccount);
    }
}

class UnsafeAccount implements Account{
    // private BigDecimal balance;
    private AtomicReference<BigDecimal> ref;    // 使用原子引用解决资源共享问题

    public UnsafeAccount(BigDecimal balance) {
        this.ref = new AtomicReference<>(balance);
    }
    @Override
    public BigDecimal getBalance() {
        return this.ref.get();
    }

    // @Override
    // public void withdraw(BigDecimal amount) {
    //     this.ref.updateAndGet(balance -> balance.subtract(amount));
    // }

    @Override
    public void withdraw(BigDecimal amount) {
        BigDecimal prev;
        BigDecimal next;
        do {
            prev = ref.get();
            next = prev.subtract(amount);
        } while (!ref.compareAndSet(prev, next));
    }
}
interface Account {

    BigDecimal getBalance();

    void withdraw(BigDecimal amount);

    static void demo(Account account) {
        List<Thread> threads = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                account.withdraw(new BigDecimal(10));
            });
            threads.add(thread);
            thread.start();
        }
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        long end = System.currentTimeMillis();
        System.out.println("balance：" + account.getBalance()
                + ", time：" + (end - start) + "ms");
    }
}


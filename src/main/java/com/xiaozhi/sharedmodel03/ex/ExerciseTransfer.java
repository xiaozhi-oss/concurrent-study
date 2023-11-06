package com.xiaozhi.sharedmodel03.ex;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author xiaozhi
 * <p>
 * 转账
 */
@Slf4j(topic = "TransferMoney")
public class ExerciseTransfer {

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(2000);
        Account b = new Account(2000);
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                a.transfer(b, randomAmount());
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                b.transfer(a, randomAmount());
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("转账前后总金额：{}", a.getMoney() + b.getMoney());
    }

    // Random 为线程安全
    static Random random = new Random();

    // 随机 1~100
    public static int randomAmount() {
        return random.nextInt(100) + 1;
    }
}

class Account {

    private int money;

    public Account(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * 转账
     *
     * @param target 转账账户
     * @param money  金额
     */
    public void transfer(Account target, int money) {
        synchronized (Account.class) {
            if (this.money >= money) {
                this.setMoney(this.getMoney() - money);
                target.setMoney(target.getMoney() + money);
            }
        }
    }
}



package com.xiaozhi.sharedmodel03.ex;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

/**
 * @author xiaozhi
 *
 * 卖票
 */
@Slf4j(topic = "Ticket")
public class ExerciseSell {

    public static void main(String[] args) throws InterruptedException {
        TicketWindow window = new TicketWindow(2000);
        List<Thread> threads = new ArrayList<>();
        List<Integer> sellCount = new Vector<>();
        for (int i = 0; i < 2000; i++) {
            Thread thread = new Thread(() -> {
                int count = window.sell(randomCount());
                sellCount.add(count);
            });
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        log.debug("售出票：{}", sellCount.stream().mapToInt(i -> i).sum());
        log.debug("余票：{}", window.getCount());
    }

    public static Random random = new Random();
    public static int randomCount() {
        return random.nextInt(5) + 1;   // 生成1-5
    }
}

class TicketWindow {

    private int count;      // 总票数

    public TicketWindow(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    /**
     * 购票
     * @param amount    票数
     * @return          购买的票数
     */
    public synchronized int sell(int amount) {
        if (this.count >= amount) {  // 如果有票
            this.count -= amount;
            return amount;
        }
        return 0;
    }
}

package com.xiaozhi.unlocked05.atimoc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author xiaozhi
 *
 * 累加器性能比较
 */
public class AccumulatorComparison {

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            demo(
                    () -> new AtomicInteger(0),
                    AtomicInteger::getAndIncrement
            );
        }
        System.out.println("=============================");
        for (int i = 0; i < 5; i++) {
            demo(
                    LongAdder::new,
                    LongAdder::increment
            );
        }
    }

    public static <T> void demo(Supplier<T> adderSupplier, Consumer<T> consumer) {
        T adder = adderSupplier.get();
        long start = System.nanoTime();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 5000_00; j++) {
                    consumer.accept(adder);
                }
            }));
        }
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        long end = System.nanoTime();
        System.out.println(adder + " 耗时：" + (end - start) / 1000_000 + "ns");
    }
}

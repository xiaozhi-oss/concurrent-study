package com.xiaozhi.unlocked05.atimoc;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author xiaozhi
 *
 * 引用数组测试
 */
@Slf4j(topic = "AtomicArrayTest")
public class AtomicArrayTest {

    public static void main(String[] args) {
        demo(
                () -> new int[5],
                array -> array.length,
                (array, index) -> array[index]++,
                array -> System.out.println("res：" + Arrays.toString(array))
        );
        demo(
                () -> new AtomicIntegerArray(5),
                AtomicIntegerArray::length,
                AtomicIntegerArray::incrementAndGet,
                System.out::println
        );
    }

    /**
     * demo
     * @param arraySupplier     提供数组
     * @param countFun          获取数组元素个数
     * @param putConsumer       自增方法，回传数组index
     * @param printConsumer     打印数组方法
     */
    public static <T> void demo(Supplier<T> arraySupplier,
                                Function<T, Integer> countFun,
                                BiConsumer<T, Integer> putConsumer,
                                Consumer<T> printConsumer) {
        List<Thread> threads = new ArrayList<>();   // 保存 Thread
        T array = arraySupplier.get();  // 获取数组
        Integer count = countFun.apply(array);
        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000_0; j++) {
                    // 这里 index 不能使用 i，以为lambda 中的外部变量必须要 final 修饰，i最后的值1000_0
                    putConsumer.accept(array, j % count);
                }
            });
            threads.add(thread);
            thread.start();
        }
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        printConsumer.accept(array);
    }
}

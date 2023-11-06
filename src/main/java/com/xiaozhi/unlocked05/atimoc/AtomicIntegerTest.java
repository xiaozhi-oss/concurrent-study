package com.xiaozhi.unlocked05.atimoc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xiaozhi
 */
public class AtomicIntegerTest {

    public static void main(String[] args) {
        AtomicInteger a = new AtomicInteger(0);
        System.out.println("========= 获取值 =======");
        System.out.println(a.get());

        System.out.println("========= i加加 和 加加i OP =======");
        System.out.println("incrementAndGet：" + a.incrementAndGet());
        System.out.println("getAndIncrement：" + a.getAndIncrement());

        System.out.println("========= 加法减法 OP =======");
        System.out.println("addAndGet(5)：" + a.addAndGet(5));
        System.out.println("getAndAdd(5)：" + a.getAndAdd(5));

        System.out.println("========= CAS OP =======");
        System.out.println("compareAndSet(2, 5)：" + a.compareAndSet(2, 5));
        System.out.println("get：" + a.get());

        System.out.println("========= 更新和获取值（lambda表达式） =====");
        System.out.println("更新并获取：" + a.updateAndGet(x -> x * 2));
        System.out.println("先获取再更新：" + a.getAndUpdate(x -> x / 2));

        System.out.println("======== 计算和获取值 ========");
        System.out.println("accumulateAndGet：" + a.accumulateAndGet(2, (p, x) -> p + x));
        System.out.println("getAndAccumulate：" + a.getAndAccumulate(-2, (p, x) ->  p + x));   // p 是还未修改前的值，x是第一个参数
        System.out.println("get：" + a.get());
    }
}

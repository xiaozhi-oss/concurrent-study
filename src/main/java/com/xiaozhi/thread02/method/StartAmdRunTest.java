package com.xiaozhi.thread02.method;

import com.xiaozhi.overview01.FileReader;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 */
@Slf4j(topic = "startAndRun.test")
public class StartAmdRunTest {

    public static void main(String[] args) {
        run();
        start2();
        // start();
    }

    public static void start() {
        System.out.println("============= start ===========");
        Thread t1 = new Thread(() -> {
            log.debug("线程名为：{}", Thread.currentThread().getName());
            // 读取文件操作
            FileReader.read();
        }, "t1");
        t1.start();
    }

    public static void run() {
        System.out.println("============= run ===========");
        Thread t2 = new Thread(() -> {
            log.debug("线程名为：{}", Thread.currentThread().getName());
            // 读取文件操作
            FileReader.read();
        }, "t2");
        t2.run();
    }

    public static void start2() {
        System.out.println("============= start ===========");
        Thread t1 = new Thread(() -> {
            new Thread(() -> {
                log.debug("阿巴阿巴：{}", Thread.currentThread().getName());
            }, "test").start();
        }, "t1");
        t1.start();
    }
}

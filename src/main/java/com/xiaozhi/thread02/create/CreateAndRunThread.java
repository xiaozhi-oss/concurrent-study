package com.xiaozhi.thread02.create;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author xiaozhi
 */
@Slf4j(topic = "CreateAndRunThreadTest")
public class CreateAndRunThread {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 方式一
        new Thread("方式一") {
            @Override
            public void run() {
                log.debug("方式一执行任务...");
            }
        }.start();
        // 方式二：
        new Thread(new Runnable() {
            @Override
            public void run() {
                log.debug("方式二执行任务...");
            }
        }, "方式二").start();

        // 方式三
        FutureTask<Integer> task = new FutureTask<>(() -> {
            log.debug("方式三执行任务...");
            return 100;
        });
        new Thread(task, "方式三").start();
        Integer result = task.get();
        log.debug("结果是：{}", result);
    }
}

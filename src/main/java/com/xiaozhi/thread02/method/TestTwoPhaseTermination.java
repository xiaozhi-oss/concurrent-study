package com.xiaozhi.thread02.method;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaozhi
 *
 * 两阶段终止模式
 */
public class TestTwoPhaseTermination {

    public static void main(String[] args) {
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();

        TimeUtil.sleep(3);
        twoPhaseTermination.stop();

    }
}

@Slf4j(topic = "TwoPhaseTermination")
class TwoPhaseTermination {

    private Thread monitor;     // 监控线程

    // 启动线程
    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                Thread currentThread = Thread.currentThread();
                if (currentThread.isInterrupted()) {
                    log.debug("料理后事...");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("监控中...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    currentThread.interrupt();      // 重新设置打印标记
                }
            }
        }, "monitor");
        monitor.start();
    }

    // 停止线程
    public void stop() {
        monitor.interrupt();
    }
}
package com.xiaozhi.memory04;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author xiaozhi
 *
 * 两阶段终止模式 plus 版
 */
@Slf4j(topic = "TestTwoPhaseTerminationPlus")
public class TestTwoPhaseTerminationPlus {

    public static void main(String[] args) {
        TwoPhaseTermination phaseTermination = new TwoPhaseTermination();
        phaseTermination.start();

        TimeUtil.sleep(3.5);
        phaseTermination.stop();
    }
}

@Slf4j(topic = "TwoPhaseTermination")
class TwoPhaseTermination {

    private Thread monitor;
    private volatile boolean isStop = false;

    public void start() {
        monitor = new Thread(() -> {
            while (true) {
                if (isStop) {
                    log.debug("料理后事...");
                    break;
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                    log.debug("记录监控日志...");
                } catch (InterruptedException e) {
                    log.debug("程序打断...");
                }
            }
        }, "monitor");
        monitor.start();
    }
    public void stop() {
        this.isStop = true;
        monitor.interrupt();
    }
}

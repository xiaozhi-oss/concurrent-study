package com.xiaozhi.memory04;

import com.xiaozhi.utils.TimeUtil;

/**
 * @author xiaozhi
 *
 * 退不出的循环
 */
public class LoopDoesNotExit {
    static volatile boolean isStop = false;
    public static void main(String[] args) {
        new Thread(() -> {
            while (!isStop) {

            }
        }).start();
        System.out.println("停止...");
        TimeUtil.sleep(1);
        isStop = true;
    }
}

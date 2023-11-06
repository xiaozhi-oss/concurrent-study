package com.xiaozhi.overview01;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 *
 * 异步测试
 */
@Slf4j(topic = "c.async")
public class AsyncTest {

    public static void main(String[] args) {
        new Thread(() -> FileReader.read()).start();
        log.info("do other things...");
    }
}

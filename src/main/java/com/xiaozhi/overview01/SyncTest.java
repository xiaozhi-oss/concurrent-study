package com.xiaozhi.overview01;

import lombok.extern.slf4j.Slf4j;

/**
 * @author xiaozhi
 *
 * 异步测试
 */
@Slf4j(topic = "c.sync")
public class SyncTest {

    public static void main(String[] args) {
        FileReader.read();
        log.info("do other things...");
    }
}

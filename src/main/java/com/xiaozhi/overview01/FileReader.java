package com.xiaozhi.overview01;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;

/**
 * @author xiaozhi
 */
@Slf4j(topic = "c.FileReader")
public class FileReader {

    public static final String FILE = "D:\\Default\\Videos\\8月30日.mp4";

    @SneakyThrows
    public static void read() {
        log.info("start...");
        long startTime = System.currentTimeMillis();
        FileInputStream fis = new FileInputStream(FILE);
        byte[] bytes = new byte[1024];
        int n = 0;
        while ((n = fis.read(bytes)) != -1) {
            String str = new String(bytes);
        }
        long endTime = System.currentTimeMillis();
        log.info("end... 耗时：" + ( endTime - startTime ) + "ms");
    }
}

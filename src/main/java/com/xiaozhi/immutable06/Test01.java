package com.xiaozhi.immutable06;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;

/**
 * @author xiaozhi
 * <p>
 * 不可变类和可变类
 */
@Slf4j(topic = "Test01")
public class Test01 {

    public static void main(String[] args) {
        // simpleDateFormatTest();
        // dateTimeFormatterTest();
        char[] chars = {'a', 'b', 'c', 'd', 'e'};
        char[] chars1 = new char[5];
        chars1 = Arrays.copyOf(chars, 5);
        String string = Arrays.toString(chars1);
        System.out.println(string);
    }

    public static void simpleDateFormatTest() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                synchronized (Date.class) {
                    try {
                        Date date = dateFormat.parse("2023-01-01");
                        log.debug("date：{}", date);
                    } catch (ParseException e) {
                        log.error("{}", e.toString());
                    }
                }
            }).start();
        }
    }

    public static void dateTimeFormatterTest() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                LocalDate date = dateFormatter.parse("2023-10-10", LocalDate::from);
                log.debug("date：{}", date);
            }).start();
        }
    }
}

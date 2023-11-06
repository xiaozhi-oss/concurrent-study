package com.xiaozhi.sharedmodel03;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * @author xiaozhi
 */
@Slf4j
public class Test05 {

    public static void main(String[] args) throws InterruptedException {
        Number number = new Number();
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                TimeUtil.sleep(1);
                number.test("v" + finalI);
            });
            thread.start();
            list.add(thread);
        }
        for (Thread thread : list) {
            thread.join();
        }
        log.debug("输出key={}", number.map.get("key"));
    }
}

class Number {

    Map<String, String> map = new Hashtable<>();

    public void test(String value) {
        if (map.get("key") == null) {
            map.put("key", value);
        }
    }
}

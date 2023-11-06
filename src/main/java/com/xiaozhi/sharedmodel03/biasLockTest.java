package com.xiaozhi.sharedmodel03;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * @author xiaozhi
 */
@Slf4j(topic = "biasLockTest")
public class biasLockTest {

    public static void main(String[] args) {
        Dog d = new Dog();
        ClassLayout classLayout = ClassLayout.parseInstance(d);
        String str = classLayout.toPrintable();
        int start = str.indexOf("0x");
        synchronized (d) {
            log.debug(classLayout.toPrintable());
        }
        log.debug(classLayout.toPrintable());
    }
}

class Dog {

}

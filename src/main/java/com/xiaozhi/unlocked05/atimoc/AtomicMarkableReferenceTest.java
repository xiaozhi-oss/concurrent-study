package com.xiaozhi.unlocked05.atimoc;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author xiaozhi
 */
@Slf4j(topic = "AtomicMarkableReferenceTest")
public class AtomicMarkableReferenceTest {
    public static void main(String[] args) {
        Person person = new Person(true);
        AtomicMarkableReference<Person> ref = new AtomicMarkableReference<Person>(person, true);
        new Thread(() -> {
            // 是 true 的就让设置为 false
            if (ref.isMarked()) {
                log.debug("它饿了，吃了一顿，肚子好饱...");
                person.setAbdomen(false);
                ref.compareAndSet(person, person, true, false);
            }
        }, "eat").start();
        TimeUtil.sleep(0.5);
        new Thread(() -> {
            if (!ref.isMarked()) {
                log.debug("它拉了，又饿了...");
                person.setAbdomen(true);
                ref.compareAndSet(person, person, false, true);
            }
        }, "la").start();
        TimeUtil.sleep(0.5);
        log.debug("最后它" + person.toString());
    }
}

class Person {
    private boolean abdomen;
    public Person(boolean abdomen) {
        this.abdomen = abdomen;
    }
    public void setAbdomen(boolean abdomen) {
        this.abdomen = abdomen;
    }

    @Override
    public String toString() {
        if (abdomen) {
            return "肚子好饿";
        } else {
            return "吃饱拉出";
        }
    }
}
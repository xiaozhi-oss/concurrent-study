package com.xiaozhi.memory04;

import java.util.HashSet;

/**
 * @author xiaozhi
 *
 * 同步模式之 Balking
 */
public class BalkingModel {

    public static void main(String[] args) {

    }
}

class Person {
    private Person person = null;
    private Person() {}
    public synchronized Person getPerson() {
        if (this.person == null) {
            this.person = new Person();
        }
        HashSet<Object> objects = new HashSet<>();
        return this.person;
    }
}


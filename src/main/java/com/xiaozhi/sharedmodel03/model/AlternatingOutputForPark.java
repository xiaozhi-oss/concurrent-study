package com.xiaozhi.sharedmodel03.model;

import java.util.concurrent.locks.LockSupport;

/**
 * @author xiaozhi
 */
public class AlternatingOutputForPark {
    static Thread t1 = null;
    static Thread t2 = null;
    static Thread t3 = null;

    public static void main(String[] args) {
        SyncPark syncPark = new SyncPark(5);
        t1 = new Thread(() -> { syncPark.print("a", t2); });
        t2 = new Thread(() -> { syncPark.print("b", t3); });
        t3 = new Thread(() -> { syncPark.print("c", t1); });
        t1.start();
        t2.start();
        t3.start();
        LockSupport.unpark(t1);
    }
}

class SyncPark {
    private final int loopNum;
    public SyncPark(int loopNum) {
        this.loopNum = loopNum;
    }
    public void print(String str, Thread nextThread) {
       for (int i = 0; i < loopNum; i++) {
           LockSupport.park();
           System.out.print(str);
           LockSupport.unpark(nextThread);
       }
   }
}

package com.xiaozhi.sharedmodel03.model;

/**
 * @author xiaozhi
 */
public class AlternatingOutputWaitNotify {

    public static void main(String[] args) {
        SyncWaitNotify waitNotify = new SyncWaitNotify(1, 5);
        new Thread(() -> { waitNotify.print("a", 1, 2); }).start();
        new Thread(() -> { waitNotify.print("b", 2, 3); }).start();
        new Thread(() -> { waitNotify.print("c", 3, 1); }).start();
    }
}

class SyncWaitNotify {
    private final int loopNum;
    private int flag;

    public SyncWaitNotify(int flag, int loopNum) {
        this.flag = flag;
        this.loopNum = loopNum;
    }

    public void print(String str, int curFlag, int nextFlag) {
        for (int i = 0; i < this.loopNum; i++) {
            synchronized (this) {
                while (this.flag != curFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.print(str);
                this.flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}

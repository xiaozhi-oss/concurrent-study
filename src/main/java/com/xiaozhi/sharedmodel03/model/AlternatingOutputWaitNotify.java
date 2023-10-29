package com.xiaozhi.sharedmodel03.model;

/**
 * @author xiaozhi
 */
public class AlternatingOutputWaitNotify {

    public static void main(String[] args) {
        SyncWaitNotify notify = new SyncWaitNotify(1, 5);
        new Thread(() -> { notify.print("a", 1, 2); }).start();
        new Thread(() -> { notify.print("b", 2, 3); }).start();
        new Thread(() -> { notify.print("c", 3, 1); }).start();
    }
}

class SyncWaitNotify {
    private int flag;
    private final int loopNum;

    public SyncWaitNotify(int flag, int loopNum) {
        this.flag = flag;
        this.loopNum = loopNum;
    }

    public void print(String str, int waitFlag, int nextFlag) {
        for (int i = 0; i < loopNum; i++) {
            synchronized (this) {
                while (flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}

package com.xiaozhi.sharedmodel03.designpatterns;

import com.xiaozhi.utils.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * @author xiaozhi
 */
public class FuturesTest {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }
        TimeUtil.sleep(1);
        for (Integer id : MailBoxes.getIds()) {
            new Postman(id, "内容：" + id).start();
        }
    }
}
@Slf4j(topic = "People")
class People extends Thread{
    @Override
    public void run() {
        GuardedObject go = MailBoxes.createGO();
        log.debug("开始收信 id:{}", go.getId());
        Object mail = go.get(5000);
        log.debug("收到信 id：{}，内容；{}", go.getId(), mail);
    }
}
@Slf4j(topic = "Postman")
class Postman extends Thread {
    private int id;
    private Object mail;

    public Postman(int id, Object mail) {
        this.id = id;
        this.mail = mail;
    }
    @Override
    public void run() {
        GuardedObject go = MailBoxes.getGuardedObject(this.id);
        log.debug("送信 id:{}, 内容:{}", id, mail);
        go.complete(mail);
    }
}

class MailBoxes {

    private static Map<Integer, GuardedObject> BOXES = new Hashtable<>();

    private static int id = 1;

    // 获取唯一id
    public static synchronized int generateId() {
        return id++;
    }

    // 通过 ID 获取 GO
    public static GuardedObject getGuardedObject(int id) {
        return BOXES.remove(id);
    }
    public static GuardedObject createGO() {
        GuardedObject go = new GuardedObject(generateId());
        BOXES.put(go.getId(), go);
        return go;
    }
    public static Set<Integer> getIds() {
        return BOXES.keySet();
    }
}
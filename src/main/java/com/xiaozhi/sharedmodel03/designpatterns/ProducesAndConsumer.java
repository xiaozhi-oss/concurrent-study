package com.xiaozhi.sharedmodel03.designpatterns;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * @author xiaozhi
 *
 * 生产者和消费者
 */
@Slf4j(topic = "ProducesAndConsumer")
public class ProducesAndConsumer {

    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(2);
        for (int i = 0; i < 3; i++) {
            final int id = i;
            new Thread(() -> {
                Message message = new Message(id, "我是靓仔：" + id);
                messageQueue.put(message);
                log.debug("生产一条消息");
            }, "生产者" + i).start();
        }
        new Thread(() -> {
            while (true) {
                Message msg = messageQueue.take();
                log.debug("消费内容：{}", msg.getContent());
            }
        }, "消费者").start();
    }
}

@Slf4j(topic = "MessageQueue")
class MessageQueue {
    private final LinkedList<Message> LIST = new LinkedList<>();
    private int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    public Message take() {
        synchronized (LIST) {
            // 如果队列为空，那么就等待
            while (LIST.isEmpty()) {
                try {
                    log.debug("队列为空，等待中...");
                    LIST.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Message message = LIST.removeFirst();
            LIST.notifyAll();
            return message;
        }
    }

    public void put(Message message) {
        synchronized (LIST) {
            // 队列的长度达到设置的阈值，等待被消费
            while (LIST.size() >= this.capacity) {
                try {
                    log.debug("队列已满，等待中...");
                    LIST.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            LIST.addLast(message);
            LIST.notifyAll();
        }
    }
}

class Message {
    private int id;
    private Object content;

    public Message(int id, Object content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public Object getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content=" + content +
                '}';
    }
}
package com.xiaozhi.unlocked05.unsafe;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author xiaozhi
 */
public class UnsafeTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // 获取 unsafe 对象，它封装了一个获取方法
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        // 1.获取域(属性)的偏移地址
        Field id = Student.class.getDeclaredField("id");
        Field name = Student.class.getDeclaredField("name");
        long idOffset = unsafe.objectFieldOffset(id);
        long nameOffset = unsafe.objectFieldOffset(name);

        // 2.执行 cas 操作
        Student student = new Student();
        unsafe.compareAndSwapObject(student, idOffset, 0, 1);
        unsafe.compareAndSwapObject(student, nameOffset, null, "张三");

        // 3.验证
        System.out.println(student);
    }
}
@Data
class Student {
    private int id;
    private String name;
}
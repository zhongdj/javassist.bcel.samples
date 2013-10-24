package net.madz.lifecycle.demo.standalone;

import java.lang.reflect.Method;

public class PrimitiveDemo {

    public static void main(String[] args) throws Throwable {
        Method method = PrimitiveDemo.class.getDeclaredMethod("foo", long.class, long.class, long.class);
        method.invoke(new PrimitiveDemo(), 5L, 6L, 7L);
        
        Class<?> class1 = Class.forName(Long.TYPE.getName());
        System.out.println(class1.getName());
    }

    public void foo(long x, long y, long z) {
    }
}

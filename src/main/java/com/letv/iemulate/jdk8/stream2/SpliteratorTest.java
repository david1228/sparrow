package com.letv.iemulate.jdk8.stream2;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

/**
 * Created by David.Liu on 17/4/30.
 */
public class SpliteratorTest {
    public static void main(String[] args) {
        Consumer consumer = a -> System.out.println(a);
        IntConsumer intConsumer = i -> System.out.println(i);
        System.out.println(consumer instanceof Consumer);
        System.out.println(intConsumer instanceof  IntConsumer);

        test(consumer); // 面向对象方式
        test(intConsumer::accept); // 函数式方式, 为什么也执行的test2的方法的else逻辑？因为仍然是一个函数式方式，并非引用传递
        test(a -> System.out.println(a)); //  函数式方式

        InnerIntConsumer intConsumer1 = new InnerIntConsumer();
        System.out.println(intConsumer1 instanceof IntConsumer);
        test(intConsumer1); // 面向对象方式，引用传递
    }

    public static void test(Consumer<? super Integer> action) {
        if (action instanceof IntConsumer) {
            System.out.println("action instanceof IntConsumer");
            tryAdvance((IntConsumer) action);
        } else {
            System.out.println("(IntConsumer) action::accept");
            tryAdvance((IntConsumer) action::accept);
        }
    }

    public static void tryAdvance(IntConsumer intConsumer) {
        intConsumer.accept(1000);
    }

    static class InnerIntConsumer implements Consumer, IntConsumer {
        @Override public void accept(int value) {
            System.out.println("InnerIntConsumer accept value: " + value);
        }

        @Override public void accept(Object o) {
            System.out.println("InnerIntConsumer accept object: " + o);
        }
    }
}

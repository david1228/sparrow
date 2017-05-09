package com.zoo.sparrow.jdk8.base;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * List各种遍历，包括函数式接口方式遍历
 *
 */
public class LambdaTest1 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3);

        for (int i = 0; i < list.size(); i++) {
            System.out.println("for1:" + i);
        }

        /**
         * 外部迭代
         */
        for (Integer i: list) {
            System.out.println("for2:" + i);
        }

        // 内部迭代，jdk8中新增遍历方式
        list.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer value) {
                System.out.println("forEach:" + value);
            }
        });

        // jdk8中使用lambda表达式遍历，没声明类型，是因为编译器可以自动推断出来的。
        list.forEach(value -> {
            System.out.println("lambda:" + value);
        });

        // 实际上，上述跟声明类型是等价的，如果声明类型需要加上()括号，这样可读性更好些。
        list.forEach((Integer value) -> {
            System.out.println("lambda add type:" + value);
        });

        // ::遍历方式，实际也是引用的Consumer函数式接口的.
        list.forEach(System.out::println);
    }
}

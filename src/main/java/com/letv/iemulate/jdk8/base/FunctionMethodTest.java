package com.letv.iemulate.jdk8.base;

import java.util.function.Function;

/**
 * Created by liudewei1228 on 17/3/20.
 */
public class FunctionMethodTest {

    public static void main(String[] args) {
        FunctionMethodTest functionTestLess7 = new FunctionMethodTest();
        System.out.println(functionTestLess7.applyCompose(2, value -> value + 1, value -> value * value)); // 5
        System.out.println(functionTestLess7.applyAndThen(2, value -> value + 2, value -> value * value)); // 16
    }

    // Function的deafault的compose方法，先计算function2然后将function2的结果作为function1函数的输入.
    public int applyCompose(int a, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
        return function1.compose(function2).apply(a);
    }

    // Function的default的andThen方法，先计算function1函数然后将function1结果作为function2函数的输入.
    public int applyAndThen(int a, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
        return function1.andThen(function2).apply(a);
    }

}

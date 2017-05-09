package com.letv.iemulate.jdk8.base;

import java.util.function.Function;

/**
 * Created by liudewei1228 on 17/3/20.
 */
public class FunctionTest {

    public static void main(String[] args) {
        FunctionTest testLess6 = new FunctionTest();
        System.out.println(testLess6.compute(1, value -> 1 + value));
        System.out.println(testLess6.compute(2, value -> {return 2 * value;}));
        System.out.println(testLess6.compute(3, value -> value * value));
        System.out.println(testLess6.compute(10, (Integer value) -> {value = value + 10; return value * value;}));

        // 可以先定义一个函数，然后将函数引用传递过去
        Function<Integer, Integer> function = (Integer value) -> { value = value + 10; return value * value;};
        System.out.println("定义函数引用：" + testLess6.compute(5, function));
    }


    // 定义一个计算函数来体会理解函数式，传递的行为，不仅仅是值
    public int compute(int a, Function<Integer, Integer> func){
        return func.apply(a);
    }

}

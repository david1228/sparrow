package com.letv.iemulate.jdk8.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by david on 17/3/11.
 */
public class LambdaTest2 {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "hello world");

        // 小写转换为大写  [MAC idea: ctrl + alt + L 格式化代码]
        for (String str : list) {
            System.out.println("nomal for:" + str.toUpperCase());
        }

        // -> 形式
        list.forEach(item -> System.out.println("forEach:" + item.toUpperCase()));

        // 添加到另外一个集合并且输出
        List<String> list2 = new ArrayList<>();
        list.forEach(item -> list2.add(item.toUpperCase()));
        list2.forEach(item -> System.out.println("list to list2:"+item));

        // Stream API初步
        // 全部使用->形式
        list.stream().map(item -> item.toUpperCase()).forEach(item -> System.out.println("-> stream1:" + item));
        // map中使用::形式, String::toUppperCase点击::可以找到函数式接口，注意toUpperCase函数就是Function.apply函数的输入，String类型的结果为输出。
        list.stream().map(String::toUpperCase).forEach(item -> System.out.println(":: stream2:"+item));
        // 全部使用::形式
        list.stream().map(String::toUpperCase).forEach(System.out::println);
    }
}

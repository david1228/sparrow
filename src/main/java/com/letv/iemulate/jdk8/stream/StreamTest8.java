package com.letv.iemulate.jdk8.stream;

import java.util.Arrays;
import java.util.List;

/**
 * Stream 流的短路与并发流
 * 什么叫做短路？ 比如 && || 逻辑运算符，对于&&，当第一个判断为false，或者||，当第一个元素判断为true，则第二个原则都不会执行。
 *
 * Created by David.Liu on 17/4/3.
 */
public class StreamTest8 {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "helloworld");
        // 长度为5，打印第一个单词
//        list.stream().filter(item -> item.length() == 5).findAny().ifPresent(item -> System.out.println(item.length()));

        // 可能有可能没有所以调用findFirst返回了OptionalInt对象
//        list.stream().mapToInt(item -> item.length()).filter(len -> len == 5).findFirst().ifPresent(System.out::println);

        // 为什么只输出了hello?
        // 流-》容器，容器里存放的每个元素的"操作"，当对流迭代判断过滤会拿容器中的操作逐个应用到元素上，并且是串行化操作.
        // 对item元素引用长度等于5并且findFirst第一个元素，所以只输出hello单词，故也说明流是存在短路运算的.
        list.stream().mapToInt(item -> {
            int length = item.length();
            System.out.println("item:" +item);
            return length;
        }).filter(len -> len == 5).findFirst().ifPresent(System.out::println);
    }
}

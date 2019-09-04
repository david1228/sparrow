package com.zoo.sparrow.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David.Liu on 17/4/4.
 */
public class StreamTest10 {

    public static void main(String[] args) {
        List<String> list1 = Arrays.asList("Hi", "Hello", "你好");
        List<String> list2 = Arrays.asList("zhagnsan", "lisi", "wangwu");

        // 输出两个集合的交叉组合，Hizhagnsan  Hilisi Hiwangwu Hellozhagnsan...

        List<String> list = list1.stream().flatMap(item1 -> list2.stream().map(item -> item1 + item))
                .collect(Collectors.toList());
        list.forEach(System.out::println);
    }
}

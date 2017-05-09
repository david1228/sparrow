package com.zoo.sparrow.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Stream 短路与并行流
 * <p>
 * Created by David.Liu on 17/4/3.
 */
public class StreamTest9 {
    public static void main(String[] args) {
        // 集合中的单词去重并输出
        List<String> list = Arrays.asList("hello world", "world hello", "hello welcome", "hello world hello");
//        list.stream().map(item -> item.split(" ")).distinct().collect(Collectors.toList()).
//                forEach(item -> Arrays.asList(item).forEach(System.out::println));

       Set<String> sets = list.stream().
               map(item -> item.split(" ")).
               flatMap(item -> Arrays.stream(item)).distinct().
               // sorted((item1, item2) -> item2.compareTo(item1)).
               collect(Collectors.toSet());
       sets.forEach(System.out::println);

    }
}

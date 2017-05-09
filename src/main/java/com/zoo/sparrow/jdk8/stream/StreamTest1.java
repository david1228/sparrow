package com.zoo.sparrow.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 将集合转换为Stream对象的三种常用方法
 *
 * Created by David.Liu on 17/3/28.
 */
public class StreamTest1 {

    public static void main(String[] args) {
        List list = java.util.Arrays.asList("hello", "world", "hello world");
        Stream stream1 = list.stream(); // 最常用方法
        Stream stream2 = Stream.of(list);

        String[] array = new String[]{"hello", "world", "hello world"};
        Stream<String> stream3 = java.util.Arrays.stream(array);
        Arrays.asList(array).stream();
    }
}

package com.zoo.sparrow.jdk8.stream;

import java.util.Arrays;
import java.util.List;

/**
 * Stream 深入学习实践
 * 函数式编程实现: 找出流中大于2的元素，然后将每个元素乘以2， 然后忽略掉流中前两个元素，然后再取流中的前两个元素，最后求出流中元素的总和。
 *
 * Created by David.Liu on 17/3/28.
 */
public class StreamTest6 {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 3, 5, 7, 9, 11);
        list.stream().filter(item -> item > 2).map(item -> item * 2).skip(2).limit(2);
        System.out.println(list.stream().filter(item -> item > 2).mapToInt(item -> item * 2).skip(2).limit(2).sum());

        // 取最小的或者最大的，为什么是带Optional的？
        list.stream().filter(item -> item > 2).mapToInt(item -> item * 2).skip(2).limit(2).min().ifPresent(System.out::println);


        // 对数组进行自然排序和反排序
        List<Integer> list2 = Arrays.asList(3, 1, 9, 7, 5, 11);
        list2.stream().sorted().forEach(System.out::println);
        list2.stream().sorted((item1, item2) -> item2 -item1).forEach(System.out::println);
    }
}

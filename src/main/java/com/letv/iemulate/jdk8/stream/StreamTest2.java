package com.letv.iemulate.jdk8.stream;

import java.util.stream.IntStream;

/**
 * 利用Stream实现功能，将一个int值 * 2之后加总
 *
 * Created by David.Liu on 17/3/28.
 */
public class StreamTest2 {

    public static void main(String[] args) {
        System.out.println(IntStream.of(2,3,4).sum());
        System.out.println("------");
        IntStream.of(1,4,6).forEach(System.out::println);
        System.out.println("------");
        IntStream.range(3, 8).forEach(System.out::println);

        // 将一个int值 * 2之后加总
        // 传统方式，大家都懂
        // Stream方式
        System.out.println("------");
        System.out.println(IntStream.of(1, 2, 3, 4, 5, 6).map(v -> v * 2 ).sum());
        System.out.println(IntStream.of(1, 2, 3, 4, 5, 6).map(v -> v * 2 ).reduce(0, Integer::sum));
        System.out.println(IntStream.of(1, 2, 3, 4, 5, 6).map(v -> v * 2 ).reduce(0, (a, b) -> a+b));
    }
}

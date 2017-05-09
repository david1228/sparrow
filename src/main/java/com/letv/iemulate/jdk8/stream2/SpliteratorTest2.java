package com.letv.iemulate.jdk8.stream2;

import java.util.Arrays;
import java.util.List;

/**
 * Created by David.Liu on 17/5/1.
 */
public class SpliteratorTest2 {
    public static void main(String[] args) {
        System.out.println("只有源的流过程原理剖析：");
        List<String> list = Arrays.asList("hello", "world", "hello world");
        list.stream().forEach(System.out::println);

        System.out.println("带有中间操作的流过程原理剖析：");
        list.stream().map(item -> item).filter(item -> true).forEach(System.out::println);
    }
}

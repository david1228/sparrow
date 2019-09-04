package com.zoo.sparrow.jdk;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by David.Liu on 17/8/6.
 */
public class StreamCreateTest {
    public static void main(String[] args) {

        Stream.generate(Math::random).limit(10).forEach(System.out::println);
        Stream.iterate(1, item -> item * 2).limit(10).forEach(System.out::println);


        List<Integer> ints = Lists.newArrayList(1,2,3,4,5,6,7,8,9,10);
        System.out.println("ints sum is:" + ints.stream().reduce(0, (sum, item) -> sum + item));
    }
}

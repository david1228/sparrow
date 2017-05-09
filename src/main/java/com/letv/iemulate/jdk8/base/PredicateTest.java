package com.letv.iemulate.jdk8.base;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Predicate函数式接口的使用
 * Represents a predicate (boolean-valued function) of one argument.
 *
 * Created by liudewei1228 on 17/3/23.
 */
public class PredicateTest {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        PredicateTest predicateTest = new PredicateTest();
        Predicate<Integer> predicate1 = item -> item > 5;
        predicateTest.conditionFilter(list, predicate1);
        System.out.println("----------大于5");
        predicateTest.conditionFilter(list, predicate1);
        System.out.println("----------偶数");
        predicateTest.conditionFilter(list, item -> item % 2 == 0);
        System.out.println("----------奇数");
        predicateTest.conditionFilter(list, item -> item % 2 != 0);
        System.out.println("----------小于5");
        predicateTest.conditionFilter(list, item -> item < 5);
        System.out.println("----------遍历all");
        predicateTest.conditionFilter(list, item -> true);
        System.out.println("----------不输出任何元素");
        predicateTest.conditionFilter(list, item -> false);
    }

    public void conditionFilter(List<Integer> list, Predicate<Integer> predicate) {
        list.forEach(a -> {
            if (predicate.test(a)) {
                System.out.println(a);
            }
        });
    }

}

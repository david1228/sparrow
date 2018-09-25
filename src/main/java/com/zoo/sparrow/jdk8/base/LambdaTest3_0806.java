package com.zoo.sparrow.jdk8.base;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by David.Liu on 17/8/6.
 */
public class LambdaTest3_0806 {
    public static void main(String[] args) {

        String[] array = {"a", "b", "c"};
        for(final Integer i : Lists.newArrayList(1,2,3)){
            Stream.of(array).map(item -> Strings.padEnd(item, i, '@')).forEach(System.out::println);
        }

        System.out.println(Strings.padEnd("2010",5, '!'));;

        // lambda表达式中的访问的外部变量必须是不可变得 。
//        String[] array2 = {"a", "b", "c"};
//        for(int i = 1; i<4; i++){
//            Stream.of(array2).map(item -> Strings.padEnd(item, i, '@')).forEach(System.out::println);
//        }

        //这里省略list的构造
        List<String> names = Lists.newArrayList("zhangsan", "lisi", "wangwu");
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        Collections.sort(names, (o1, o2) -> o1.compareTo(o2));
    }
}

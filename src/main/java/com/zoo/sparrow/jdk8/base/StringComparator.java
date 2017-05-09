package com.zoo.sparrow.jdk8.base;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by liudewei1228 on 17/3/20.
 */
public class StringComparator {

    public static void main(String[] args) {

        // 对集合倒序排列
        List<String> names = Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu");

        // 常用方式
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareToIgnoreCase(o1);
            }
        });

        for (String name : names) {
            System.out.println("jdk:"+name);
        }

        // lambda表达式，学习语法:(type1 arg1, type2 arg2) -> {return statement;};
//        Collections.sort(names, (String o2, String o1) -> {return o2.compareToIgnoreCase(o1);});
//        Collections.sort(names, (o2, o1) -> o2.compareToIgnoreCase(o1)); // expression，如果只有一条语句可不用加花括号
//        Collections.sort(names, (o2, o1) -> {return o2.compareToIgnoreCase(o1);});


        Collections.sort(names, Collections.reverseOrder());
        // 打印结果
        names.forEach(name -> System.out.println("lambda:"+name));

    }

}

package com.zoo.sparrow.jdk8.stream;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream 深入学习实践
 *
 * Created by David.Liu on 17/3/28.
 */
public class StreamTest5 {

    public static void main(String[] args) {

        List<String> list = Lists.newArrayList("hello", "world", "helloworld");

        // 问问自己，如何用函数式编程来实现, 将元素转换为大写（转换为另外一个结果），首先想到的是用映射方法
//        list.stream().map(str -> str.toUpperCase()); //  Function<? super T, ? extends R> mapper 输入一个参数返回一个结果

        // 一行代码搞定，是不是很爽？
        list.stream().map(String::toUpperCase).collect(Collectors.toList()).forEach(System.out::println);


        //  求下列数组中，每个集合中元素的平方？
        //  首先要明确map方法和flatmap区别： map是直接一个list中每个元素转换为对应的不同元素；
        //  flatmap理解，举个例子：比如1）有三个arrayList 2）每个不同的list中元素得到不同的结果【等价于map的过程】 将2）中三个集合中所有元素打平映射到一个"集合"中。

        Stream<List<Integer>> listStream = Stream.of(Arrays.asList(1), Arrays.asList(2, 3, 4), Arrays.asList(4, 5, 6));
        listStream.flatMap(theList -> theList.stream()).map(item -> item * item).collect(Collectors.toList()).forEach(System.out::println);


        // flatmap联系，来自于java8action, 返回去重后的单个单词比如H e l o W r d
        List<String> words = Arrays.asList("Hello", "World");
//        String[] words = new String[]{"Hello", "World"};
//        Stream<String> stringStream = Arrays.stream(words);
        // 通过map(Arrays::stream)返回的是Stream<Stream<String>>对象
        System.out.println("--map(Arrays::stream)");
        words.stream().map(wd -> wd.split("")).map(Arrays::stream).distinct().forEach(wd -> System.out.print(wd + " "));
        System.out.println("--flatmap(Arrays::stream)");
        // 通过flatmap(Arrays::stream), 将Arrays::stream返回多个Stream<String>里面的元素打平为一个流
        words.stream().map(wd -> wd.split("")).flatMap(Arrays::stream).distinct().forEach(wd -> System.out.print(wd + " "));
    }
}

package com.letv.iemulate.jdk8.stream2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by David.Liu on 17/4/17.
 */
public class MyCompartorPractice {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("nihao", "hello", "world", "welcom");

//        Collections.sort(list);
//        Collections.sort(list, (item1, item2) -> item1.compareTo(item2));

        // 按照长度升序排序
//        Collections.sort(list, (item1, item2) -> item1.length() - item2.length());

        // 按长度倒序排序
//        Collections.sort(list, (item1, item2) -> item2.length() - item1.length());

//        System.out.println("-------");
//        list.forEach(System.out::println);

        // 通过Comparator自带的默认方法来实现字母倒序排列
//        Collections.sort(list, Comparator.comparingInt(String::length).reversed());
//        System.out.println("-------Comparator.comparingInt");
//        list.forEach(System.out::println);
//
        // 替换为lambda表达式方式
//        Collections.sort(list, Comparator.comparingInt((String item) -> item.length()).reversed());
        /*
         通过IDE我们看到了item.length()调用报错了，提示不能解析方法length，查看item类型为Object对象，为什么呢？这是因为编译器无法自动推断出来
         那为什么编译器不能推断呢？思考下，我们进入comparingInt方法去看下，public static <T> Comparator<T> comparingInt(ToIntFunction<? super T> keyExtractor)
         这个方法中ToIntFunction<? super T>这里并不是精确的T类型，标识T类型或者T类型之上的类型(父类型...)，如果不指定类型，编译器无法准确识别
         所以，在这里comparingInt参数中使用(String item) 或者 (Object item)都是可以的，假设你传了个(Boolean item) -> 1 ，一定是报错的，可以试试！

        Collections.sort(list, (item1, item2) -> item1.compareTo(item2));
        这里面只有一层，是直接进行比较的，编译器能够自动识别出来。

        Collections.sort(list, Comparator.comparingInt((String item) -> item.length()).reversed());
        这个跟上面的区别是第二个参数是reversed方法返回的，这个方法返回的是Comparator<T>泛型只是T类型，离上下文是最接近的，而Comparator.comparingInt是离上下文比较远的。

        所以注意Java8中的lambda表达式的类型推断跟上下文关系密切。
          */
        // list.stream().. Stream<T>这里的T显然编译器能给后面的方法自动识别。

        list.sort(Comparator.comparingInt(String::length).reversed());

        // thenCompararing
//        Collections.sort(list, Comparator.comparingInt(String::length).thenComparing(String::toLowerCase, Collections.reverseOrder()));

        // thenComparaing排序主要在comparingInt为0的结果，不等于0的不做排序
//        Collections.sort(list, Comparator.comparingInt(String::length).reversed().thenComparing(String::toLowerCase, Collections.reverseOrder()));

        // 最后一个比较器无效，因为第二个比较器两两比较不为0的【如果为0，则会继续执行下一个比较器的。】，说明是一定有先后顺序的。
        Collections.sort(list, Comparator.comparingInt(String::length).reversed().
                thenComparing(String::toLowerCase, Collections.reverseOrder()).
                thenComparing(Comparator.reverseOrder()));
        System.out.println(list);
     }
}

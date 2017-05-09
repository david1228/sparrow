package com.letv.iemulate.jdk8.stream;

import java.util.stream.Stream;

/**
 * Stream 深入学习实践
 *
 * Created by David.Liu on 17/3/28.
 */
public class StreamTest3 {

    public static void main(String[] args) {
//        Stream<String> streamStr = Stream.of("hello", "world", "hello world");
        // 先查看方法声明，看接受的方法类型
        // 不能同时两种方式转换为诶String[],要注释掉，否则：Exception in thread "main" java.lang.IllegalStateException: stream has already been operated upon or closed
//        String[] stringArr = streamStr.toArray(length -> new String[length]);
//        Arrays.asList(stringArr).forEach(System.out::println);

        // 方法引用
//        System.out.println("-----");
//        String[] stringArr2 = streamStr.toArray(String[]::new);
//        Arrays.asList(stringArr2).forEach(System.out::println);

        System.out.println("-----");
        Stream<String> stream = Stream.of("hello", "world", "hello world");
//        stream.collect(Collectors.toList()).forEach(System.out::println);

        // collect方法的使用
        /*
            通过lambda表达式实现，
            深入详解：
            第一个参数要返回的结果集合theList，
            第二参数遍历的每个元素添加到集合中，
            第三个参数将第二次(上一次)得到的每一个list添加到最终theList当中
            最终返回的theList实际就是ArrayList的对象。
         */
//        stream.collect(() -> Lists.newArrayList(),
//                (theList, item) -> theList.add(item),
//                (theList, theList2) -> theList.addAll(theList2)).forEach(System.out::println);

        // 通过方法引用实现
//        stream.collect(LinkedList::new, LinkedList::add, LinkedList::addAll).forEach(System.out::println);

        // 字符串拼接的示例，最简单
//        String strResult1 = stream.collect(StringBuffer::new, StringBuffer::append, StringBuffer::append).toString();
//        System.out.println(strResult1);

        // 拼接逗号分隔的字符串
//        String strResult2 = stream.collect(() -> new StringBuilder(),
//                (stringBuilder, item) -> stringBuilder.append(item).append(","),
//                (stringBuilder, secondStrBuilder) -> stringBuilder.append(secondStrBuilder.toString())).toString();
//        System.out.println(strResult2);

        // 使用Collectors工具类字符串逗号连接
//        String strResult3 = stream.collect(Collectors.joining(",")).toString();
//        System.out.println(strResult3);

//        stream.collect(Collectors.toCollection(ArrayList::new));

        // 使用Collectors工具类转换为TreeSet
//        TreeSet treeSet = stream.collect(Collectors.toCollection(TreeSet::new));
//        treeSet.forEach(System.out::println);

    }
}

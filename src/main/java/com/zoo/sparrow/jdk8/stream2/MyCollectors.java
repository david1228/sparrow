package com.zoo.sparrow.jdk8.stream2;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiConsumer;

/**
 * Created by David.Liu on 17/4/20.
 */
public class MyCollectors {

    public static void main(String[] args) {
        ss();
    }

    public static void ss(){
        HashSet<String> sa = new HashSet<>();
        BiConsumer<Set<String>, String> b = Set<String>::add;
        b.accept(sa, "set-1");

        // 非静态方法不能在静态上下文中被引用
//        BiConsumer<Set<String>, String> c = TreeSet<String>::add;
//        c.accept(sa, "treeset-1");

        BiConsumer<TreeSet<String>, String> c = TreeSet<String>::add;

        sa.forEach(System.out::println);
    }

}

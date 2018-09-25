package com.zoo.sparrow.jdk8.other;

/**
 * Created by David.Liu on 17/8/11.
 */
public class StringTest {

    public static void main(String[] args) {
        System.out.println(String.join(",", new String[]{"hello", "world"}));
        System.out.println(String.format("http://%s", "127.0.0.1"));
    }
}

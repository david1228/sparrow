package com.letv.iemulate.jdk8.base;

/**
 * Created by David.Liu on 17/3/26.
 */
public class GuavaTest {
    public static void main(String[] args) {
        com.google.common.base.Optional optional = com.google.common.base.Optional.of("hello");
        System.out.println(optional);
    }
}

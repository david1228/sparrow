package com.zoo.sparrow.jdk;

import java.util.stream.Stream;

/**
 * Created by David.Liu on 17/8/6.
 */
public class SubClass1 implements Interface1{


    public static void main(String[] args) {
        SubClass1 subClass1 = new SubClass1();
        System.out.println(Interface1.name);
        System.out.println(subClass1.name);

    }

}

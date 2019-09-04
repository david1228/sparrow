package com.zoo.sparrow.jdk;

/**
 * Created by David.Liu on 17/8/6.
 */
public class SubClass2 extends AbstractClass1{
    String name = "lisi";
    public static void main(String[] args) {
        SubClass2 sub = new SubClass2();
        System.out.println(sub.name);
    }
}
